package pl.mycar.carservice.service.report;

import java.nio.file.Path;
import java.security.Principal;

public interface ReportService {

  Path getReport(Long carId, Principal principal);

}
