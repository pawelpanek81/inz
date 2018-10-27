package pl.mycar.technicalexaminationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mycar.technicalexaminationservice.exception.CarNotFoundException;
import pl.mycar.technicalexaminationservice.feign.CarClient;
import pl.mycar.technicalexaminationservice.feign.ReadCarDTO;
import pl.mycar.technicalexaminationservice.mapper.ExaminationMapper;
import pl.mycar.technicalexaminationservice.model.dto.CreateExaminationDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationEntity;
import pl.mycar.technicalexaminationservice.persistence.repository.ExaminationRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExaminationServiceImpl implements ExaminationService {
  private ExaminationRepository examinationRepository;
  private CarClient carClient;

  @Autowired
  public ExaminationServiceImpl(ExaminationRepository examinationRepository, CarClient carClient) {
    this.examinationRepository = examinationRepository;
    this.carClient = carClient;
  }

  @Override
  public void createExamination(CreateExaminationDTO dto, Principal principal) {
    getCarInfo(dto.getCarId());
    ExaminationEntity examinationEntity = ExaminationMapper.mapToEntity(dto);
    examinationEntity.setUsername(principal.getName());
    examinationRepository.save(examinationEntity);
  }

  @Override
  public List<ReadExaminationDTO> readAllExaminations(Principal principal) {
    String username = principal.getName();
    return examinationRepository.findAllByUsername(username).stream()
        .map(entity -> {
          ReadExaminationDTO dto = ExaminationMapper.toDTOMapper.apply(entity);
          dto.setCar(getCarInfo(entity.getCarId()));
          return dto;
        })
        .collect(Collectors.toList());
  }

  @Override
  public ReadExaminationDTO readLastExamination(Principal principal) {
    return null;
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
