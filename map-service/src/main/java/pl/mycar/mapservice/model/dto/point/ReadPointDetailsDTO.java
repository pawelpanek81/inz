package pl.mycar.mapservice.model.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadPointDetailsDTO {
  private ReadMapPointDTO mapPoint;
  private Long ratingCount;
  private Double averageRating;
}
