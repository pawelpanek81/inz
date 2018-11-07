package pl.mycar.technicalexaminationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.technicalexaminationservice.exception.DocumentNotFoundException;
import pl.mycar.technicalexaminationservice.exception.ExaminationNotFoundException;
import pl.mycar.technicalexaminationservice.exception.UnauthorizedException;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationDocumentEntity;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationEntity;
import pl.mycar.technicalexaminationservice.persistence.repository.ExaminationDocumentRepository;
import pl.mycar.technicalexaminationservice.persistence.repository.ExaminationRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

@Service
public class ExaminationDocumentServiceImpl implements ExaminationDocumentService {
  private ExaminationDocumentRepository examinationDocumentRepository;
  private ExaminationRepository examinationRepository;

  @Autowired
  public ExaminationDocumentServiceImpl(ExaminationDocumentRepository examinationDocumentRepository,
                                        ExaminationRepository examinationRepository) {
    this.examinationDocumentRepository = examinationDocumentRepository;
    this.examinationRepository = examinationRepository;
  }

  @Override
  public void addDocument(MultipartFile file, Long examinationId) {
    Optional<ExaminationEntity> optionalOfExamination = examinationRepository.findById(examinationId);

    if (!optionalOfExamination.isPresent()) {
      throw new ExaminationNotFoundException();
    }

    try {
      ExaminationDocumentEntity entity = new ExaminationDocumentEntity(
          null,
          optionalOfExamination.get(),
          file.getOriginalFilename(),
          file.getBytes()
      );
      examinationDocumentRepository.save(entity);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public byte[] getDocument(Long documentId, Principal principal) {
    Optional<ExaminationDocumentEntity> optionalOfDocument = examinationDocumentRepository.findById(documentId);
    if (!optionalOfDocument.isPresent()) {
      throw new DocumentNotFoundException();
    }
    ExaminationDocumentEntity entity = optionalOfDocument.get();
    String username = principal.getName();
    if (!entity.getExamination().getUsername().equals(username)) {
      throw new UnauthorizedException();
    }
    return entity.getContent();
  }
}
