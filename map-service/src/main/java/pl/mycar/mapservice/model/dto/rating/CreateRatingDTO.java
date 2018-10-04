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

  private String rating;

  @AssertTrue
  public boolean checkRating() {
    Double aDouble = Double.valueOf(rating);
    String unifiedRating = rating.replace(",", ".");
    String digitAfterPoint = unifiedRating.split(".")[1];

    return aDouble.compareTo(1.0) >= 0 &&
        aDouble.compareTo(5.0) <= 0 &&
        digitAfterPoint.equals("0") || digitAfterPoint.equals("5");
  }
}
