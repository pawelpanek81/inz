package pl.mycar.mapservice.mapper;

import pl.mycar.mapservice.model.dto.type.ReadPointTypeDTO;
import pl.mycar.mapservice.persistence.entity.PointTypeEntity;

import java.util.function.Function;

public class PointTypeMapper {

  public static Function<? super PointTypeEntity, ? extends ReadPointTypeDTO> toDTOMapper = entity ->
      new ReadPointTypeDTO(
          entity.getId(),
          entity.getType(),
          entity.getDescription(),
          entity.getIconFile()
      );
}
