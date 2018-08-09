package pl.mycar.mapservice.model.dto.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePointTypeDTO {
  private String type;
  private String description;
  private String iconFile;
}
