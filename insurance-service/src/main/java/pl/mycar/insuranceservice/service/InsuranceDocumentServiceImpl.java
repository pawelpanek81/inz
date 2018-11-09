package pl.mycar.insuranceservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.insuranceservice.exception.DocumentNotFoundException;
import pl.mycar.insuranceservice.exception.InsuranceNotFoundException;
import pl.mycar.insuranceservice.exception.UnauthorizedException;
import pl.mycar.insuranceservice.persistence.entity.InsuranceDocumentEntity;
import pl.mycar.insuranceservice.persistence.entity.InsuranceEntity;
import pl.mycar.insuranceservice.persistence.repository.InsuranceDocumentRepository;
import pl.mycar.insuranceservice.persistence.repository.InsuranceRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Service
public class InsuranceDocumentServiceImpl implements InsuranceDocumentService {
  private InsuranceRepository insuranceRepository;
  private InsuranceDocumentRepository insuranceDocumentRepository;

  @Autowired
  public InsuranceDocumentServiceImpl(InsuranceRepository insuranceRepository,
                                      InsuranceDocumentRepository insuranceDocumentRepository) {
    this.insuranceRepository = insuranceRepository;
    this.insuranceDocumentRepository = insuranceDocumentRepository;
  }

  @Override
  public void addDocument(MultipartFile file, Long insuranceId) {
    Optional<InsuranceEntity> optionalOfInsurance = insuranceRepository.findById(insuranceId);

    if (!optionalOfInsurance.isPresent()) {
      throw new InsuranceNotFoundException();
    }

    try {
      InsuranceDocumentEntity entity = new InsuranceDocumentEntity(
          null,
          optionalOfInsurance.get(),
          file.getOriginalFilename(),
          file.getBytes()
      );
      insuranceDocumentRepository.save(entity);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public byte[] getDocument(Long documentId, Principal principal) {
    Optional<InsuranceDocumentEntity> optionalOfDocument = insuranceDocumentRepository.findById(documentId);
    if (!optionalOfDocument.isPresent()) {
      throw new DocumentNotFoundException();
    }
    InsuranceDocumentEntity entity = optionalOfDocument.get();
    String username = principal.getName();
    if (!entity.getInsurance().getUsername().equals(username)) {
      throw new UnauthorizedException();
    }
    return entity.getContent();
  }
}
