package pl.mycar.mapservice.mapper;

import pl.mycar.mapservice.model.dto.comment.CreateCommentDTO;
import pl.mycar.mapservice.model.dto.comment.ReadCommentDTO;
import pl.mycar.mapservice.persistence.entity.RatingCommentEntity;

import java.util.function.Function;

public class RatingCommentMapper {

  public static Function<? super RatingCommentEntity, ? extends ReadCommentDTO> toDTOMapper = entity ->
      new ReadCommentDTO(
          entity.getId(),
          entity.getAddedBy(),
          entity.getComment(),
          entity.getAddedAt()
      );

  public static RatingCommentEntity mapToEntity(CreateCommentDTO dto) {
    return new RatingCommentEntity(
        null,
        null,
        null,
        dto.getComment(),
        null
    );
  }
}
