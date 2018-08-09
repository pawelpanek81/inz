package pl.mycar.mapservice.model.dto.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReadPointTypeDTO {
  private Long id;
  private String type;
  private String description;
  private String iconFile;
}
