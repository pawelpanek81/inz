package pl.mycar.technicalexaminationservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.technicalexaminationservice.exception.CarNotFoundException;
import pl.mycar.technicalexaminationservice.model.dto.CreateExaminationDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;
import pl.mycar.technicalexaminationservice.service.ExaminationDocumentService;
import pl.mycar.technicalexaminationservice.service.ExaminationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("examinations")
public class ExaminationController {
  private ExaminationService examinationService;
  private ExaminationDocumentService examinationDocumentService;

  @Autowired
  public ExaminationController(ExaminationService examinationService, ExaminationDocumentService examinationDocumentService) {
    this.examinationService = examinationService;
    this.examinationDocumentService = examinationDocumentService;
  }

  @GetMapping("")
  @Secured("ROLE_USER")
  ResponseEntity<Page<ReadExaminationDTO>> getAllExaminations(@RequestParam(value = "carId") Long carId,
                                                              Pageable pageable,
                                                              Principal principal) {
    Page<ReadExaminationDTO> examinationDTOS = examinationService.readAllExaminationsByCarId(carId, principal, pageable);
    return ResponseEntity.ok(examinationDTOS);
  }

  @GetMapping("/last-examination")
  ResponseEntity<ReadExaminationDTO> getLastExamination(@RequestParam(value = "carId") Long carId, Principal principal) {
    ReadExaminationDTO readExaminationDTO = examinationService.readLastExamination(carId, principal);
    if (readExaminationDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(readExaminationDTO);
  }

  @PostMapping("")
  @Secured("ROLE_USER")
  ResponseEntity<?> addExamination(CreateExaminationDTO dto,
                                   @RequestParam(value = "multipartfiles") List<MultipartFile> files,
                                   Principal principal) {
    try {
      ReadExaminationDTO createdExaminationDto = examinationService.createExamination(dto, principal);
      files.forEach(file -> examinationDocumentService.addDocument(file, createdExaminationDto.getId()));

    } catch (CarNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok().build();
  }

}
