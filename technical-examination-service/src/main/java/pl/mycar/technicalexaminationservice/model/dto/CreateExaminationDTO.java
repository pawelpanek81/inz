package pl.mycar.technicalexaminationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExaminationDTO {
  @NotNull
  private LocalDate examinationDate;

  @NotNull
  private Long carId;

  private String description;
}
