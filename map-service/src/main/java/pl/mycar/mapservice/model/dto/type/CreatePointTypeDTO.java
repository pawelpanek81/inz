package pl.mycar.mapservice.model.dto.type;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePointTypeDTO {
  @NotBlank
  private String type;

  @NotBlank
  private String description;

  @NotBlank
  private String iconFile;
}
