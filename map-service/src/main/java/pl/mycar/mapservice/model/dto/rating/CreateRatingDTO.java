package pl.mycar.mapservice.model.dto.rating;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class CreateRatingDTO {
  @NotBlank
  private String header;

  @NotBlank
  private String comment;

  private Integer rating;

  @AssertTrue
  public boolean checkRating() {
    return rating >=1 && rating <= 5;
  }
}
