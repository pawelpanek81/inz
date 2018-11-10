package pl.mycar.insuranceservice.service;

import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.insuranceservice.exception.CarNotFoundException;
import pl.mycar.insuranceservice.exception.DatesHaveCommonPartException;
import pl.mycar.insuranceservice.exception.InsuranceNotFoundException;
import pl.mycar.insuranceservice.exception.InvalidFilesException;
import pl.mycar.insuranceservice.feign.CarClient;
import pl.mycar.insuranceservice.feign.ReadCarDTO;
import pl.mycar.insuranceservice.model.database.InsuranceType;
import pl.mycar.insuranceservice.model.dto.CreateInsuranceDTO;
import pl.mycar.insuranceservice.model.dto.ReadDocumentDTO;
import pl.mycar.insuranceservice.model.dto.ReadInsuranceDTO;
import pl.mycar.insuranceservice.persistence.entity.InsuranceDocumentEntity;
import pl.mycar.insuranceservice.persistence.entity.InsuranceEntity;
import pl.mycar.insuranceservice.persistence.repository.InsuranceDocumentRepository;
import pl.mycar.insuranceservice.persistence.repository.InsuranceRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InsuranceServiceImpl implements InsuranceService {
  private static final List<String> ALLOWED_EXTENSION_LIST = Arrays.asList("pdf", "jpg", "jpeg", "gif", "png", "bmp");
  private static final int ALLOWED_MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024;
  private static final int ALLOWED_MAX_FILES_COUNT = 5;
  private InsuranceRepository insuranceRepository;
  private InsuranceDocumentRepository insuranceDocumentRepository;
  private CarClient carClient;
  private ModelMapper modelMapper;

  @Autowired
  public InsuranceServiceImpl(InsuranceRepository insuranceRepository,
                              InsuranceDocumentRepository insuranceDocumentRepository,
                              CarClient carClient,
                              ModelMapper modelMapper) {
    this.insuranceRepository = insuranceRepository;
    this.insuranceDocumentRepository = insuranceDocumentRepository;
    this.carClient = carClient;
    this.modelMapper = modelMapper;
  }

  @Override
  public ReadInsuranceDTO createInsurance(CreateInsuranceDTO dto, Principal principal) {
    if (!datesValid(dto, principal)) {
      throw new DatesHaveCommonPartException();
    }

    getCarInfo(dto.getCarId());
    InsuranceEntity insuranceEntity = modelMapper.map(dto, InsuranceEntity.class);
    insuranceEntity.setUsername(principal.getName());
    InsuranceEntity save = insuranceRepository.save(insuranceEntity);
    return readInsuranceById(save.getId(), principal);
  }

  private boolean datesValid(CreateInsuranceDTO dto, Principal principal) {
    List<InsuranceEntity> insurances = insuranceRepository
        .findAllByCarIdAndUsernameAndType(dto.getCarId(), principal.getName(), dto.getType());

    for (InsuranceEntity insuranceEntity : insurances) {
      LocalDate existingFromDate = insuranceEntity.getFromDate();
      LocalDate existingToDate = insuranceEntity.getToDate();

      if (existingFromDate.isBefore(dto.getFromDate()) &&
          dto.getFromDate().isBefore(existingToDate)) {
        return false;
      }
      if (existingFromDate.isBefore(dto.getToDate()) &&
          dto.getToDate().isBefore(existingToDate)) {
        return false;
      }
      if (dto.getFromDate().equals(existingFromDate)) {
        return false;
      }
      if (dto.getToDate().equals(existingToDate)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public ReadInsuranceDTO readInsuranceById(Long insuranceId, Principal principal) {
    String username = principal.getName();
    Optional<InsuranceEntity> optionalOfInsurance = insuranceRepository.findByIdAndUsername(insuranceId, username);

    if (!optionalOfInsurance.isPresent()) {
      throw new InsuranceNotFoundException();
    }
    InsuranceEntity insuranceEntity = optionalOfInsurance.get();
    ReadInsuranceDTO dto = modelMapper.map(insuranceEntity, ReadInsuranceDTO.class);
    dto.setCar(getCarInfo(insuranceEntity.getCarId()));
    List<InsuranceDocumentEntity> documents = insuranceDocumentRepository.findAllByInsuranceId(dto.getId());
    List<ReadDocumentDTO> documentDTOS = documents.stream()
        .map(doc -> new ReadDocumentDTO(doc.getFileName(), doc.getId()))
        .collect(Collectors.toList());
    dto.setDocuments(documentDTOS);
    return dto;
  }

  @Override
  public Page<ReadInsuranceDTO> readAllInsurancesByCarId(Long carId,
                                                         Principal principal,
                                                         Pageable pageable) {
    String username = principal.getName();
    return insuranceRepository.findAllByCarIdAndUsername(carId, username, pageable)
        .map(entity -> {
          ReadInsuranceDTO dto = modelMapper.map(entity, ReadInsuranceDTO.class);
          dto.setCar(getCarInfo(entity.getCarId()));
          List<InsuranceDocumentEntity> documents = insuranceDocumentRepository.findAllByInsuranceId(entity.getId());
          List<ReadDocumentDTO> documentDTOS = documents.stream()
              .map(doc -> new ReadDocumentDTO(doc.getFileName(), doc.getId()))
              .collect(Collectors.toList());
          dto.setDocuments(documentDTOS);
          return dto;
        });
  }

  @Override
  public ReadInsuranceDTO readLastThirdPartyInsurance(Long carId, Principal principal) {
    String username = principal.getName();
    Optional<InsuranceEntity> optionalOfInsurance =
        insuranceRepository.findTop1ByCarIdAndUsernameAndTypeOrderByToDateDescId(carId, username, InsuranceType.THIRD_PARTY);
    if (!optionalOfInsurance.isPresent()) {
      return null;
    }
    return readInsuranceById(optionalOfInsurance.get().getId(), principal);
  }

  @Override
  public ReadInsuranceDTO readLastFullyInsurance(Long carId, Principal principal) {
    String username = principal.getName();
    Optional<InsuranceEntity> optionalOfInsurance =
        insuranceRepository.findTop1ByCarIdAndUsernameAndTypeOrderByToDateDescId(carId, username, InsuranceType.FULLY);
    if (!optionalOfInsurance.isPresent()) {
      return null;
    }
    return readInsuranceById(optionalOfInsurance.get().getId(), principal);
  }

  @Override
  public void validateFiles(List<MultipartFile> files) {
    if (files.size() > ALLOWED_MAX_FILES_COUNT) {
      throw new InvalidFilesException();
    }
    for (MultipartFile file : files) {
      String extension = FilenameUtils.getExtension(file.getOriginalFilename());
      if (!ALLOWED_EXTENSION_LIST.contains(extension) || file.getSize() > ALLOWED_MAX_FILE_SIZE_BYTES) {
        throw new InvalidFilesException();
      }
    }
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
