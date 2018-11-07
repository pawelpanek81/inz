package pl.mycar.technicalexaminationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.mycar.technicalexaminationservice.model.dto.CreateExaminationDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;

import java.security.Principal;

public interface ExaminationService {
  ReadExaminationDTO createExamination(CreateExaminationDTO dto, Principal principal);

  ReadExaminationDTO readExaminationById(Long carId, Principal principal);

  Page<ReadExaminationDTO> readAllExaminationsByCarId(Long carId, Principal principal, Pageable pageable);

  ReadExaminationDTO readLastExamination(Long carId, Principal principal);
}
