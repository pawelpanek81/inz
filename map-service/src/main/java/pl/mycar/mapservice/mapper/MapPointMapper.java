package pl.mycar.mapservice.mapper;

import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.type.ReadPointTypeDTO;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;
import pl.mycar.mapservice.persistence.entity.PointTypeEntity;

import java.util.function.Function;

public class MapPointMapper {

  public static Function<? super MapPointEntity, ? extends ReadMapPointDTO> toDTOMapper = entity ->
      ReadMapPointDTO.builder()
          .id(entity.getId())
          .addedBy(entity.getAddedBy())
          .companyName(entity.getCompanyName())
          .info(entity.getInfo())
          .address(entity.getAddress())
          .zipCode(entity.getZipCode())
          .www(entity.getWww())
          .phone(entity.getPhone())
          .city(entity.getCity())
          .latitude(entity.getLatitude())
          .longitude(entity.getLongitude())
          .addedAt(entity.getAddedAt())
          .type(PointTypeMapper.toDTOMapper.apply(entity.getPointType()))
          .build();

  public static MapPointEntity mapToEntity(CreateMapPointDTO dto) {
    return MapPointEntity.builder()
        .companyName(dto.getCompanyName())
        .info(dto.getInfo())
        .address(dto.getAddress())
        .zipCode(dto.getZipCode())
        .www(dto.getWww())
        .phone(dto.getPhone())
        .city(dto.getCity())
        .latitude(dto.getLatitude())
        .longitude(dto.getLongitude())
        .pointType(null)
        .approved(false)
        .build();
  }

  static class PointTypeMapper {
    static Function<? super PointTypeEntity, ? extends ReadPointTypeDTO> toDTOMapper = entity ->
        new ReadPointTypeDTO(
            entity.getId(),
            entity.getType(),
            entity.getDescription(),
            entity.getIconFile()
        );
  }
}
