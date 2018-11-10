package pl.mycar.carservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface ServiceDocumentService {

  void addDocument(MultipartFile file, Long serviceId);

  byte[] getDocument(Long documentId, Principal principal);

}
