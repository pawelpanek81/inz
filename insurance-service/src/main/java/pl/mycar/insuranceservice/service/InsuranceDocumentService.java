package pl.mycar.insuranceservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface InsuranceDocumentService {

  void addDocument(MultipartFile file, Long insuranceId);

  byte[] getDocument(Long documentId, Principal principal);

}
