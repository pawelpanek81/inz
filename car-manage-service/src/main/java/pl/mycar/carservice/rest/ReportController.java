package pl.mycar.carservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.mycar.carservice.exception.CarNotFoundException;
import pl.mycar.carservice.exception.UnauthorizedException;
import pl.mycar.carservice.service.report.ReportService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;

@RestController
@RequestMapping("reports")
public class ReportController {
  private ReportService reportService;

  @Autowired
  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping("")
//  @Secured("ROLE_USER")
  public ResponseEntity<?> downloadReport(@RequestParam("carId") Long carId, Principal principal) {
    InputStream inputStream;
    Path report = reportService.getReport(carId, principal);
    try {
      inputStream = Files.newInputStream(report);

      InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
      HttpHeaders headers = new HttpHeaders();

      return ResponseEntity.ok()
          .headers(headers)
          .contentLength(Files.size(report))
          .contentType(MediaType.APPLICATION_PDF)
          .body(inputStreamResource);

    } catch (CarNotFoundException | UnauthorizedException e) {
      return ResponseEntity.notFound().build();
    } catch (IOException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
