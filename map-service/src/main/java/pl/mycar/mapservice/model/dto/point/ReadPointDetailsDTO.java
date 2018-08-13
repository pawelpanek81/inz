package pl.mycar.mapservice.model.dto.point;

import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;

import java.util.List;

public class ReadPointDetailsDTO {
  ReadMapPointDTO mapPoint;
  List<ReadRatingDTO> ratings;
}
