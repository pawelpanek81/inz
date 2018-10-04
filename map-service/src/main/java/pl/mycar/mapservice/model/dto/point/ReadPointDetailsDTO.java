package pl.mycar.mapservice.model.dto.point;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadPointDetailsDTO {
  private ReadMapPointDTO mapPoint;
  private List<ReadRatingDTO> ratings;
}
