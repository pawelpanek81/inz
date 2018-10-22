package pl.mycar.carservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.carservice.model.database.FuelType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarDTO {
  @NotBlank
  private String brand;

  @NotBlank
  private String model;

  @NotBlank
  private FuelType fuelType;

  @NotBlank
  private Integer engineCapacity;

  @NotBlank
  private Double enginePowerInHP;

  @NotBlank
  private Integer productionYear;

  @NotBlank
  private String VIN;
}
