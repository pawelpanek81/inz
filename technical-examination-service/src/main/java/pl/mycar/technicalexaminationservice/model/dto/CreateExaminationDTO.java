package pl.mycar.technicalexaminationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateExaminationDTO {
  private Long id;
  private LocalDate examinationDate;
  private Long carId;
  private String description;
}
