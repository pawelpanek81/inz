package pl.mycar.carservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.carservice.model.database.FuelType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCarDTO {
  private Long id;
  private String brand;
  private String model;
  private FuelType fuelType;
  private Integer engineCapacity;
  private Double enginePowerInHP;
  private Integer productionYear;
  private String vin;
}
