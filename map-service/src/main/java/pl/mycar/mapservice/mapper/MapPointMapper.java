package pl.mycar.mapservice.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mycar.mapservice.exception.PointTypeNotFoundException;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;
import pl.mycar.mapservice.persistence.entity.PointTypeEntity;
import pl.mycar.mapservice.persistence.repository.PointTypeRepository;

import java.util.Optional;
import java.util.function.Function;

@Component
public class MapPointMapper {
  private PointTypeRepository pointTypeRepository;

  @Autowired
  public MapPointMapper(PointTypeRepository pointTypeRepository) {
    this.pointTypeRepository = pointTypeRepository;
  }

  public static Function<? super MapPointEntity, ? extends ReadMapPointDTO> toDTOMapper = entity ->
      ReadMapPointDTO.builder()
          .id(entity.getId())
          .addedBy(entity.getAddedBy())
          .companyName(entity.getCompanyName())
          .info(entity.getInfo())
          .address(entity.getAddress())
          .www(entity.getWww())
          .phone(entity.getPhone())
          .city(entity.getCity())
          .latitude(entity.getLatitude())
          .longitude(entity.getLongitude())
          .addedAt(entity.getAddedAt())
          .type(entity.getPointType().getType())
          .build();

  public MapPointEntity mapToEntity(CreateMapPointDTO dto) {
    Optional<PointTypeEntity> optOfType = pointTypeRepository.findByType(dto.getType());

    if (!optOfType.isPresent()) {
      throw new PointTypeNotFoundException();
    }

    return MapPointEntity.builder()
        .companyName(dto.getCompanyName())
        .info(dto.getInfo())
        .address(dto.getAddress())
        .www(dto.getWww())
        .phone(dto.getPhone())
        .city(dto.getCity())
        .latitude(dto.getLatitude())
        .longitude(dto.getLongitude())
        .pointType(optOfType.get())
        .build();
  }
}
