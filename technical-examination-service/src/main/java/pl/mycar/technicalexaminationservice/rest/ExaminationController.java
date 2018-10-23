package pl.mycar.technicalexaminationservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;
import pl.mycar.technicalexaminationservice.service.ExaminationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("examinations")
public class ExaminationController {
  private ExaminationService examinationService;

  @Autowired
  public ExaminationController(ExaminationService examinationService) {
    this.examinationService = examinationService;
  }

  @GetMapping("")
  @Secured("ROLE_USER")
  ResponseEntity<List<ReadExaminationDTO>> getAllExaminations(Principal principal) {
    List<ReadExaminationDTO> examinationDTOS = examinationService.readAllExaminations(principal);
    return ResponseEntity.ok(examinationDTOS);
  }

}
