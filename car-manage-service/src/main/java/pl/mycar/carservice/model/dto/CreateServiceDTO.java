package pl.mycar.carservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.mycar.carservice.model.database.ServiceType;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateServiceDTO {
  @NotNull
  private Long carId;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate serviceDate;

  @NotNull
  private ServiceType serviceType;

  @NotNull
  private Double mileage;

  @NotNull
  private String header;

  @NotNull
  private String description;

  @NotNull
  private Double cost;
}
