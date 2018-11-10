package pl.mycar.carservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.carservice.exception.CarNotFoundException;
import pl.mycar.carservice.exception.InvalidFilesException;
import pl.mycar.carservice.exception.UnauthorizedException;
import pl.mycar.carservice.model.dto.CreateServiceDTO;
import pl.mycar.carservice.service.ServiceDocumentService;
import pl.mycar.carservice.service.ServiceService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("services")
public class ServiceController {
  private ServiceService serviceService;
  private ServiceDocumentService serviceDocumentService;

  @Autowired
  public ServiceController(ServiceService serviceService, ServiceDocumentService serviceDocumentService) {
    this.serviceService = serviceService;
    this.serviceDocumentService = serviceDocumentService;
  }

  @PostMapping("")
  @Secured("ROLE_USER")
  public ResponseEntity<?> addService(CreateServiceDTO dto,
                                      @RequestParam(value = "multipartfiles") List<MultipartFile> files,
                                      Principal principal) {

    try {
      serviceService.validateFiles(files);
    } catch (InvalidFilesException e) {
      return ResponseEntity.badRequest().build();
    }
    try {
      Long createdEntityId = serviceService.createService(dto, principal);
      files.forEach(file -> serviceDocumentService.addDocument(file, createdEntityId));

    } catch (CarNotFoundException | UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok().build();
  }
}
