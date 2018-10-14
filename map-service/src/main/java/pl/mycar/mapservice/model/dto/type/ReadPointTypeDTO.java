package pl.mycar.mapservice.model.dto.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadPointTypeDTO {
  private Long id;
  private String type;
  private String description;
  private String iconFile;
}
