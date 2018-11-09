package pl.mycar.insuranceservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mycar.insuranceservice.exception.UnauthorizedException;
import pl.mycar.insuranceservice.service.InsuranceDocumentService;

import java.security.Principal;

@RestController
@RequestMapping("documents")
public class DocumentController {

  private InsuranceDocumentService insuranceDocumentService;

  @Autowired
  public DocumentController(InsuranceDocumentService insuranceDocumentService) {
    this.insuranceDocumentService = insuranceDocumentService;
  }

  @GetMapping("/{id}")
  @Secured("ROLE_USER")
  ResponseEntity<byte[]> getDocument(@PathVariable Long id, Principal principal) {
    byte[] document;
    try {
      document = insuranceDocumentService.getDocument(id, principal);
    } catch (UnauthorizedException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(document);
  }


}
