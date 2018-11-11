package pl.mycar.carservice.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.carservice.exception.DocumentNotFoundException;
import pl.mycar.carservice.exception.ServiceNotFoundException;
import pl.mycar.carservice.exception.UnauthorizedException;
import pl.mycar.carservice.persistence.entity.ServiceDocumentEntity;
import pl.mycar.carservice.persistence.entity.ServiceEntity;
import pl.mycar.carservice.persistence.repository.ServiceDocumentRepository;
import pl.mycar.carservice.persistence.repository.ServiceRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceDocumentServiceImpl implements ServiceDocumentService {
  private ServiceRepository serviceRepository;
  private ServiceDocumentRepository serviceDocumentRepository;

  @Autowired
  public ServiceDocumentServiceImpl(ServiceRepository serviceRepository,
                                    ServiceDocumentRepository serviceDocumentRepository) {
    this.serviceRepository = serviceRepository;
    this.serviceDocumentRepository = serviceDocumentRepository;
  }

  @Override
  public void addDocument(MultipartFile file, Long serviceId) {
    Optional<ServiceEntity> optionalOfService = serviceRepository.findById(serviceId);

    if (!optionalOfService.isPresent()) {
      throw new ServiceNotFoundException();
    }

    try {
      ServiceDocumentEntity entity = new ServiceDocumentEntity(
          null,
          optionalOfService.get(),
          file.getOriginalFilename(),
          file.getBytes()
      );
      serviceDocumentRepository.save(entity);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public byte[] getDocument(Long documentId, Principal principal) {
    Optional<ServiceDocumentEntity> optionalOfDocument = serviceDocumentRepository.findById(documentId);
    if (!optionalOfDocument.isPresent()) {
      throw new DocumentNotFoundException();
    }
    ServiceDocumentEntity entity = optionalOfDocument.get();
    String username = principal.getName();
    if (!entity.getService().getCar().getOwner().equals(username)) {
      throw new UnauthorizedException();
    }
    return entity.getContent();
  }

  @Override
  public List<ServiceDocumentEntity> getDocuments(Long serviceId) {
    return serviceDocumentRepository.findAllByServiceId(serviceId);
  }
}
