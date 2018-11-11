package pl.mycar.carservice.service.service;

import org.springframework.web.multipart.MultipartFile;
import pl.mycar.carservice.persistence.entity.ServiceDocumentEntity;

import java.security.Principal;
import java.util.List;

public interface ServiceDocumentService {

  void addDocument(MultipartFile file, Long serviceId);

  byte[] getDocument(Long documentId, Principal principal);

  List<ServiceDocumentEntity> getDocuments(Long serviceId);

}
