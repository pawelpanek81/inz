package pl.mycar.technicalexaminationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadExaminationDTO {
  private Long id;
  private LocalDate examinationDate;
  private String car;
  private String description;
}
