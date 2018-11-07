package pl.mycar.technicalexaminationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadDocumentDTO {
  private String fileName;
  private Long documentId;
}
