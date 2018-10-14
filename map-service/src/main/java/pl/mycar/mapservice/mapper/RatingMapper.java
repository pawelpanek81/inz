package pl.mycar.mapservice.mapper;

import pl.mycar.mapservice.model.dto.rating.CreateRatingDTO;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;
import pl.mycar.mapservice.persistence.entity.RatingEntity;

import java.util.function.Function;

public class RatingMapper {

  public static Function<? super RatingEntity, ? extends ReadRatingDTO> toDTOMapper = entity -> new ReadRatingDTO(
      entity.getId(),
      entity.getAddedBy(),
      entity.getHeader(),
      entity.getComment(),
      entity.getRating(),
      entity.getAddedAt()
  );

  public static RatingEntity mapToEntity(CreateRatingDTO dto) {
    return new RatingEntity(
        null,
        null,
        dto.getHeader(),
        dto.getComment(),
        dto.getRating(),
        null,
        null
    );
  }
}
