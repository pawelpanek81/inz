package pl.mycar.technicalexaminationservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.technicalexaminationservice.exception.CarNotFoundException;
import pl.mycar.technicalexaminationservice.model.dto.CreateExaminationDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;
import pl.mycar.technicalexaminationservice.service.ExaminationService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

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

  @PostMapping("")
  @Secured("ROLE_USER")
  ResponseEntity<?> addExamination(CreateExaminationDTO dto,
                                   @RequestParam(value = "multipartfiles") List<MultipartFile> files,
                                   Principal principal) {
    try {
      examinationService.createExamination(null, principal);
    } catch (CarNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok().build();
  }

}
