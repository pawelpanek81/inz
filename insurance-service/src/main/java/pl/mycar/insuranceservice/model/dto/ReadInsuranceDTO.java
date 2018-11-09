package pl.mycar.insuranceservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.insuranceservice.model.database.InsuranceType;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadInsuranceDTO {
  private Long id;
  private LocalDate fromDate;
  private LocalDate toDate;
  private Double cost;
  private InsuranceType type;
  private String car;
  private String description;
  private List<ReadDocumentDTO> documents;
}
