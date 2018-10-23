package pl.mycar.technicalexaminationservice.service;

import pl.mycar.technicalexaminationservice.model.dto.CreateExaminationDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;

import java.security.Principal;
import java.util.List;

public interface ExaminationService {
  void createExamination(CreateExaminationDTO dto, Principal principal);

  List<ReadExaminationDTO> readAllExaminations(Principal principal);

  ReadExaminationDTO readLastExamination(Principal principal);
}
