package pl.mycar.mapservice.model.dto.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePointTypeDTO {
  @NotBlank
  private String type;

  @NotBlank
  private String description;

  @NotBlank
  private String iconFile;
}
