package pl.mycar.insuranceservice.feign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadCarDTO {
  private Long id;
  private String brand;
  private String model;
}
