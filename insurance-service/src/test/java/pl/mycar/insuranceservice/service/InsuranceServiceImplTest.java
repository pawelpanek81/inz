package pl.mycar.insuranceservice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import pl.mycar.insuranceservice.exception.DatesHaveCommonPartException;
import pl.mycar.insuranceservice.feign.CarClient;
import pl.mycar.insuranceservice.feign.ReadCarDTO;
import pl.mycar.insuranceservice.model.database.InsuranceType;
import pl.mycar.insuranceservice.model.dto.CreateInsuranceDTO;
import pl.mycar.insuranceservice.persistence.entity.InsuranceEntity;
import pl.mycar.insuranceservice.persistence.repository.InsuranceDocumentRepository;
import pl.mycar.insuranceservice.persistence.repository.InsuranceRepository;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InsuranceServiceImplTest {

  @Mock
  private InsuranceRepository insuranceRepository;

  @Mock
  private InsuranceDocumentRepository insuranceDocumentRepository;

  @Mock
  private CarClient carClient;

  @Mock
  private Principal principal;

  private ModelMapper modelMapper;
  private InsuranceService insuranceService;

  @Before
  public void init() {
    modelMapper = new ModelMapper();
    insuranceService = new InsuranceServiceImpl(insuranceRepository, insuranceDocumentRepository, carClient, modelMapper);
  }

  @Test(expected = DatesHaveCommonPartException.class)
  public void createInsuranceShouldThrowDatesHaveCommonPartExceptionIfInvalidDate() {
    // arrange
    String user = "user1";

    Long carId = 1L;
    String brand = "Mercedes";
    String model = "W201";

    LocalDate fromDate1 = LocalDate.of(2018, 11, 24);
    LocalDate toDate1 = LocalDate.of(2018, 11, 27);
    CreateInsuranceDTO dto1 = new CreateInsuranceDTO(fromDate1, toDate1, 1000.0, InsuranceType.THIRD_PARTY, carId, "");
    InsuranceEntity insuranceEntity1 = modelMapper.map(dto1, InsuranceEntity.class);
    insuranceEntity1.setUsername(principal.getName());

    when(principal.getName()).thenReturn(user);
    when(carClient.getCar(carId)).thenReturn(ResponseEntity.ok(new ReadCarDTO(carId, brand, model)));
    when(insuranceRepository.save(any())).thenReturn(insuranceEntity1);
    when(insuranceRepository.findByIdAndUsername(1L, user)).thenReturn(Optional.of(insuranceEntity1));
    insuranceService.createInsurance(dto1, principal);
    when(insuranceRepository.findAllByCarIdAndUsernameAndType(carId, user, InsuranceType.THIRD_PARTY)).thenReturn(Collections.singletonList(insuranceEntity1));

    LocalDate fromDate2 = LocalDate.of(2018, 11, 25);
    LocalDate toDate2 = LocalDate.of(2018, 11, 26);
    CreateInsuranceDTO dto2 = new CreateInsuranceDTO(fromDate2, toDate2, 1000.0, InsuranceType.THIRD_PARTY, carId, "");

    // act
    insuranceService.createInsurance(dto2, principal); //throws DatesHaveCommonPartException here

    // assert exception thrown
  }
}