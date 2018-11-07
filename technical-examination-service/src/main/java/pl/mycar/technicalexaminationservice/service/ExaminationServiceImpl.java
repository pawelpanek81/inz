package pl.mycar.technicalexaminationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mycar.technicalexaminationservice.exception.CarNotFoundException;
import pl.mycar.technicalexaminationservice.exception.ExaminationNotFoundException;
import pl.mycar.technicalexaminationservice.exception.UnauthorizedException;
import pl.mycar.technicalexaminationservice.feign.CarClient;
import pl.mycar.technicalexaminationservice.feign.ReadCarDTO;
import pl.mycar.technicalexaminationservice.mapper.ExaminationMapper;
import pl.mycar.technicalexaminationservice.model.dto.CreateExaminationDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadDocumentDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationDocumentEntity;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationEntity;
import pl.mycar.technicalexaminationservice.persistence.repository.ExaminationDocumentRepository;
import pl.mycar.technicalexaminationservice.persistence.repository.ExaminationRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExaminationServiceImpl implements ExaminationService {
  private ExaminationRepository examinationRepository;
  private ExaminationDocumentRepository examinationDocumentRepository;
  private CarClient carClient;

  @Autowired
  public ExaminationServiceImpl(ExaminationRepository examinationRepository,
                                ExaminationDocumentRepository examinationDocumentRepository,
                                CarClient carClient) {
    this.examinationRepository = examinationRepository;
    this.examinationDocumentRepository = examinationDocumentRepository;
    this.carClient = carClient;
  }

  @Override
  public ReadExaminationDTO createExamination(CreateExaminationDTO dto, Principal principal) {
    getCarInfo(dto.getCarId());
    ExaminationEntity examinationEntity = ExaminationMapper.mapToEntity(dto);
    examinationEntity.setUsername(principal.getName());
    ExaminationEntity save = examinationRepository.save(examinationEntity);
    return readExaminationById(save.getId(), principal);
  }

  @Override
  public ReadExaminationDTO readExaminationById(Long examinationId, Principal principal) {
    String username = principal.getName();
    Optional<ExaminationEntity> optionalOfExamination = examinationRepository.findByIdAndUsername(examinationId, username);

    if (!optionalOfExamination.isPresent()) {
      throw new ExaminationNotFoundException();
    }
    ExaminationEntity examinationEntity = optionalOfExamination.get();
    ReadExaminationDTO dto = ExaminationMapper.toDTOMapper.apply(examinationEntity);
    List<ExaminationDocumentEntity> documents = examinationDocumentRepository.findAllByExaminationId(dto.getId());
    List<ReadDocumentDTO> documentDTOS = documents.stream()
        .map(doc -> new ReadDocumentDTO(doc.getFileName(), doc.getId()))
        .collect(Collectors.toList());
    dto.setDocuments(documentDTOS);
    return dto;
  }

  @Override
  public Page<ReadExaminationDTO> readAllExaminationsByCarId(Long carId, Principal principal, Pageable pageable) {
    String username = principal.getName();
    return examinationRepository.findAllByCarIdAndUsername(carId, username, pageable)
        .map(entity -> {
          ReadExaminationDTO dto = ExaminationMapper.toDTOMapper.apply(entity);
          dto.setCar(getCarInfo(entity.getCarId()));
          List<ExaminationDocumentEntity> documents = examinationDocumentRepository.findAllByExaminationId(entity.getId());
          List<ReadDocumentDTO> documentDTOS = documents.stream()
              .map(doc -> new ReadDocumentDTO(doc.getFileName(), doc.getId()))
              .collect(Collectors.toList());
          dto.setDocuments(documentDTOS);
          return dto;
        });
  }

  @Override
  public ReadExaminationDTO readLastExamination(Long carId, Principal principal) {
    String username = principal.getName();
    Optional<ExaminationEntity> optionalOfExamination =
        examinationRepository.findTop1ByCarIdAndUsernameOrderByExaminationDateDescId(carId, username);
    if (!optionalOfExamination.isPresent()) {
      return null;
    }
    return readExaminationById(optionalOfExamination.get().getId(), principal);
  }

  private String getCarInfo(Long carId) {
    ResponseEntity<ReadCarDTO> carEntity = carClient.getCar(carId);
    if (carEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
      throw new CarNotFoundException();
    }
    ReadCarDTO body = Objects.requireNonNull(carEntity.getBody());
    return body.getBrand() + " " + body.getModel();
  }
}
