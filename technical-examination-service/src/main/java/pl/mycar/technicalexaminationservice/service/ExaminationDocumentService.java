package pl.mycar.technicalexaminationservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface ExaminationDocumentService {

  void addDocument(MultipartFile file, Long examinationId);

  byte[] getDocument(Long documentId, Principal principal);

}
