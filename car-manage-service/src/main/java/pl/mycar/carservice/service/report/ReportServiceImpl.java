package pl.mycar.carservice.service.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
  private static final String PDF_EXTENSION = ".pdf";

  private final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

  @Override
  public Path getReport(Long carId, Principal principal) {
    String prefix = "report-";
    String nowDateString = LocalDateTime.now().toString()
        .replace("_", "-")
        .replace(":", "-")
        .replace("T", "");
    String suffix = "-" + nowDateString + "-" + carId.toString() + PDF_EXTENSION;
    Path reportPath;

    Document document = new Document();
    try {
      reportPath = Files.createTempFile(prefix, suffix);

      logger.info("Saving report to file {}", reportPath.toFile().getAbsolutePath());

      PdfWriter.getInstance(document, new FileOutputStream(reportPath.toFile()));

    } catch (DocumentException | IOException e) {
      throw new RuntimeException(e);
    }

    document.open();
    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
    Chunk chunk = new Chunk("Hello World", font);

    try {
      document.add(chunk);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    }
    document.close();

    return reportPath;
  }

}
