package pl.mycar.mapservice.mapper;

import pl.mycar.mapservice.model.dto.rating.ReadRatingCommentDTO;
import pl.mycar.mapservice.persistence.entity.RatingCommentEntity;

import java.util.function.Function;

public class RatingCommentMapper {

  public static Function<? super RatingCommentEntity, ? extends ReadRatingCommentDTO> toDTOMapper = entity ->
      new ReadRatingCommentDTO(
          entity.getId(),
          entity.getAddedBy(),
          entity.getComment(),
          entity.getAddedAt()
      );

}
