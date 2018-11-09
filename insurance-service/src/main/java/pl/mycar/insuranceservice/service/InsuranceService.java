package pl.mycar.insuranceservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.insuranceservice.model.dto.CreateInsuranceDTO;
import pl.mycar.insuranceservice.model.dto.ReadInsuranceDTO;

import java.security.Principal;
import java.util.List;

public interface InsuranceService {

  ReadInsuranceDTO createInsurance(CreateInsuranceDTO dto, Principal principal);

  ReadInsuranceDTO readInsuranceById(Long insuranceId, Principal principal);

  Page<ReadInsuranceDTO> readAllInsurancesByCarId(Long carId, Principal principal, Pageable pageable);

  ReadInsuranceDTO readLastThirdPartyInsurance(Long carId, Principal principal);

  ReadInsuranceDTO readLastFullyInsurance(Long carId, Principal principal);

  void validateFiles(List<MultipartFile> files);
}
