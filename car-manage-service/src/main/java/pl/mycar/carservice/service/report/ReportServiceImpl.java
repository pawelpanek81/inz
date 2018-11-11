package pl.mycar.carservice.service.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycar.carservice.exception.CarNotFoundException;
import pl.mycar.carservice.persistence.entity.CarEntity;
import pl.mycar.carservice.persistence.entity.ServiceDocumentEntity;
import pl.mycar.carservice.persistence.entity.ServiceEntity;
import pl.mycar.carservice.persistence.repository.CarRepository;
import pl.mycar.carservice.persistence.repository.ServiceRepository;
import pl.mycar.carservice.service.service.ServiceDocumentService;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
  private static final String PDF_EXTENSION = ".pdf";
  private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
  private CarRepository carRepository;
  private ServiceRepository serviceRepository;
  private ServiceDocumentService serviceDocumentService;

  @Autowired
  public ReportServiceImpl(ServiceRepository serviceRepository,
                           ServiceDocumentService serviceDocumentService,
                           CarRepository carRepository) {
    this.serviceRepository = serviceRepository;
    this.serviceDocumentService = serviceDocumentService;
    this.carRepository = carRepository;
  }

  @Override
  public Path getReport(Long carId, Principal principal) {
    Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);

    if (!optionalCarEntity.isPresent()) {
      throw new CarNotFoundException();
    }

    String prefix = "report-";
    String nowDateString = LocalDateTime.now().toString()
        .replace("_", "-")
        .replace(":", "-")
        .replace("T", "");
    String suffix = "-" + nowDateString + "-" + carId.toString() + PDF_EXTENSION;
    Path reportPath;

    Document document = new Document();
    FileOutputStream os = null;
    PdfWriter writer = null;
    try {
      reportPath = Files.createTempFile(prefix, suffix);

      logger.info("Saving report to file {}", reportPath.toFile().getAbsolutePath());

      os = new FileOutputStream(reportPath.toFile());
      writer = PdfWriter.getInstance(document, os);
      writer.setPageEvent(new Header());
    } catch (DocumentException | IOException e) {
      throw new RuntimeException(e);
    }

    document.open();

    try {
      BaseFont times = BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1250, BaseFont.EMBEDDED);
      Font font = new Font(times, 12, Font.NORMAL, BaseColor.BLACK);
      Font bold = new Font(times, 12, Font.BOLD, BaseColor.BLACK);
      Chunk chunk;
      Paragraph p;

      CarEntity car = optionalCarEntity.get();

      p = new Paragraph();
      p.add(new Chunk("Raport dla auta ", font));
      p.add(new Chunk(car.getBrand(), bold));
      p.add(new Chunk(" "));
      p.add(new Chunk(car.getModel(), bold));
      p.add(new Chunk(", rocznik: ", font));
      p.add(new Chunk(car.getProductionYear().toString(), font));
      document.add(p);

      p = new Paragraph();
      p.add(new Chunk("Dane techniczne pojazdu:", font));
      document.add(p);

      com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
      list.add(new ListItem("Numer identyfikacyjny: " + car.getVin(), font));
      String fuel;
      switch (car.getFuelType()) {
        case UNLEADED:
          fuel = "Benzyna";
          break;
        case UNLEADED_LPG:
          fuel = "Benzyna + LPG";
          break;
        case UNLEADED_CNG:
          fuel = "Benzyna + CNG";
          break;
        case DIESEL:
          fuel = "Diesel";
          break;
        case HYBRID:
          fuel = "Hybryda";
          break;
        default:
          fuel = "";
      }
      list.add(new ListItem("Zasilany paliwem: " + fuel, font));
      list.add(new ListItem("Pojemność silnika: " + car.getEngineCapacity() + " centymetrów sześciennych", font));
      list.add(new ListItem("Moc: " + car.getEnginePowerInHP() + " koni mechanicznych", font));
      document.add(list);

      document.add(Chunk.NEWLINE);

      p = new Paragraph();
      p.add(new Chunk("Historia serwisowa pojazdu", font));
      p.setAlignment(Element.ALIGN_CENTER);
      document.add(p);

      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);

      List<ServiceEntity> services = serviceRepository.findAllByCarIdOrderByServiceDateDesc(carId);
      for (ServiceEntity service : services) {
        String serviceDate = service.getServiceDate().toString();
        String serviceType = "";
        switch (service.getServiceType()) {
          case EXCHANGE:
            serviceType = "wymiana";
            break;
          case REGENERATION:
            serviceType = "regeneracja";
            break;
          case OTHER:
            serviceType = "inny";
            break;
        }
        String header = service.getHeader();
        String mileage = service.getMileage().toString();
        String cost = service.getCost().toString();
        String description = service.getDescription();

        p = new Paragraph();
        p.add(new Chunk("data serwisu: " + serviceDate, font));
        p.add(new Chunk(", przebieg pojazdu: " + mileage + " kilometrów", font));
        document.add(p);

        p = new Paragraph();
        p.add(new Chunk("nazwa: ", font));
        p.add(new Chunk(header, bold));
        p.add(new Chunk(String.format(" (%s)", serviceType), font));
        document.add(p);

        p = new Paragraph();
        p.add(new Chunk("Opis: ", font));
        p.add(new Chunk(description, font));
        document.add(p);

        p = new Paragraph();
        p.add(new Chunk("Koszt: " + cost + " złotych", font));
        document.add(p);

        List<ServiceDocumentEntity> documents = serviceDocumentService.getDocuments(service.getId());

        if (!documents.isEmpty()) {
          p = new Paragraph();
          p.add(new Chunk("Załączone dokumenty:", font));
          document.add(p);
        }

        com.itextpdf.text.List l = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
        documents.forEach(doc -> l.add(new ListItem(doc.getFileName(), font)));
        document.add(l);

        for (ServiceDocumentEntity doc : documents) {
          document.newPage();
          if (!FilenameUtils.getExtension(doc.getFileName()).equals("pdf")) {
            Image img = Image.getInstance(doc.getContent());
            img.scaleToFit(
                document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin(),
                document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin()
            );
            document.add(img);
          } else {
            org.apache.pdfbox.pdmodel.PDDocument d = new org.apache.pdfbox.pdmodel.PDDocument();
//            d.load(new ByteArrayInputStream(doc.getContent()));
//            SimpleRenderer renderer = new SimpleRenderer();
//            renderer.setResolution(600);
//            List<java.awt.Image> images = renderer.render(d);
//            for (java.awt.Image img : images) {
//              Image i = Image.getInstance(img, null);
//              document.add(i);
//            }
          }
        }
      }

    } catch (DocumentException | IOException e) {
      throw new RuntimeException(e);
    }
    document.close();

    return reportPath;
  }

}

class Header extends PdfPageEventHelper {
  @Override
  public void onStartPage(PdfWriter writer, Document document) {
    PdfContentByte cb = writer.getDirectContent();
    ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, header(),
        document.right(),
        document.top() + 10,
        0);
  }

  private Phrase header() {
    Font ffont = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.ITALIC);
    String headerText = String.format(
        "Data wygenerowania raportu: %s %s",
        LocalDate.now().toString(),
        LocalTime.now().toString().substring(0, 5)
    );
    return new Phrase(headerText, ffont);
  }
}