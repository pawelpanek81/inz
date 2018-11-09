package pl.mycar.insuranceservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.insuranceservice.model.database.InsuranceType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInsuranceDTO {
  private LocalDate fromDate;
  private LocalDate toDate;
  private Double cost;
  private InsuranceType type;
  private Long carId;
  private String description;
}
