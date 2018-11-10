package pl.mycar.carservice.mapper;

import pl.mycar.carservice.model.dto.CreateServiceDTO;
import pl.mycar.carservice.persistence.entity.ServiceEntity;

public class ServiceMapper {

  public static ServiceEntity mapToEntity(CreateServiceDTO dto) {
    return new ServiceEntity(
        null,
        null,
        dto.getServiceDate(),
        dto.getServiceType(),
        dto.getMileage(),
        dto.getHeader(),
        dto.getDescription(),
        dto.getCost()
    );
  }
}
