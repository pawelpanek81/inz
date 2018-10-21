package pl.mycar.mapservice.model.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCommentDTO {
  @NotBlank
  private String comment;
}
