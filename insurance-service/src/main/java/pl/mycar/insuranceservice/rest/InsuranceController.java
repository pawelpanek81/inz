package pl.mycar.insuranceservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.insuranceservice.exception.CarNotFoundException;
import pl.mycar.insuranceservice.exception.InvalidFilesException;
import pl.mycar.insuranceservice.model.dto.CreateInsuranceDTO;
import pl.mycar.insuranceservice.model.dto.ReadInsuranceDTO;
import pl.mycar.insuranceservice.service.InsuranceDocumentService;
import pl.mycar.insuranceservice.service.InsuranceService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("insurances")
public class InsuranceController {
  private InsuranceService insuranceService;
  private InsuranceDocumentService insuranceDocumentService;

  @Autowired
  public InsuranceController(InsuranceService insuranceService, InsuranceDocumentService insuranceDocumentService) {
    this.insuranceService = insuranceService;
    this.insuranceDocumentService = insuranceDocumentService;
  }

  @GetMapping("")
  @Secured("ROLE_USER")
  ResponseEntity<Page<ReadInsuranceDTO>> getInsurancePage(@RequestParam(value = "carId") Long carId,
                                                          Principal principal,
                                                          Pageable pageable) {
    Page<ReadInsuranceDTO> insuranceDTOPage = insuranceService.readAllInsurancesByCarId(carId, principal, pageable);
    return ResponseEntity.ok(insuranceDTOPage);
  }

  @GetMapping("/last-third-party-insurance")
  @Secured("ROLE_USER")
  ResponseEntity<ReadInsuranceDTO> getLastThirdPartyInsurance(@RequestParam(value = "carId") Long carId,
                                                              Principal principal) {
    ReadInsuranceDTO readInsuranceDTO = insuranceService.readLastThirdPartyInsurance(carId, principal);
    if (readInsuranceDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(readInsuranceDTO);
  }

  @GetMapping("/last-fully-insurance")
  @Secured("ROLE_USER")
  ResponseEntity<ReadInsuranceDTO> getLastFullyInsurance(@RequestParam(value = "carId") Long carId,
                                                         Principal principal) {
    ReadInsuranceDTO readInsuranceDTO = insuranceService.readLastFullyInsurance(carId, principal);
    if (readInsuranceDTO == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(readInsuranceDTO);
  }

  @PostMapping("")
  @Secured("ROLE_USER")
  ResponseEntity<?> addInsurance(CreateInsuranceDTO dto,
                                 @RequestParam(value = "multipartfiles") List<MultipartFile> files,
                                 Principal principal) {
    try {
      insuranceService.validateFiles(files);
    } catch (InvalidFilesException e) {
      return ResponseEntity.badRequest().build();
    }
    try {
      ReadInsuranceDTO createdInsuranceDTO = insuranceService.createInsurance(dto, principal);
      files.forEach(file -> insuranceDocumentService.addDocument(file, createdInsuranceDTO.getId()));

    } catch (CarNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return ResponseEntity.ok().build();
  }

}
