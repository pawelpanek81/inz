package pl.mycar.carservice.mapper;

import pl.mycar.carservice.model.dto.CreateCarDTO;
import pl.mycar.carservice.model.dto.ReadCarDTO;
import pl.mycar.carservice.persistence.entity.CarEntity;

import java.util.function.Function;

public class CarMapper {

  public static Function<CarEntity, ReadCarDTO> toDTOMapper = carEntity -> new ReadCarDTO(
      carEntity.getId(),
      carEntity.getBrand(),
      carEntity.getModel(),
      carEntity.getFuelType(),
      carEntity.getEngineCapacity(),
      carEntity.getEnginePowerInHP(),
      carEntity.getProductionYear(),
      carEntity.getVIN()
  );

  public static CarEntity mapToEntity(CreateCarDTO dto) {
    return new CarEntity(
        null,
        null,
        dto.getBrand(),
        dto.getModel(),
        dto.getFuelType(),
        dto.getEngineCapacity(),
        dto.getEnginePowerInHP(),
        dto.getProductionYear(),
        dto.getVIN()
    );
  }
}
