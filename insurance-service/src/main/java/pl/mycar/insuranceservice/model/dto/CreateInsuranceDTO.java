package pl.mycar.insuranceservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.mycar.insuranceservice.model.database.InsuranceType;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInsuranceDTO {
  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate fromDate;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate toDate;

  @NotNull
  private Double cost;

  @NotNull
  private InsuranceType type;

  @NotNull
  private Long carId;
  private String description;

  @AssertTrue(message="fromDate must be before toDate")
  private boolean isValid() {
    return this.fromDate.isBefore(toDate);
  }
}