package pl.mycar.notificationservice.mapper;

import pl.mycar.notificationservice.model.dto.CreateNotificationDTO;
import pl.mycar.notificationservice.model.dto.ReadNotificationDTO;
import pl.mycar.notificationservice.persistence.entity.NotificationEntity;

import java.util.function.Function;

public class NotificationMapper {

  public static Function<NotificationEntity, ReadNotificationDTO> toDTOMapper = entity -> new ReadNotificationDTO(
      entity.getId(),
      entity.getAddedAt(),
      entity.getText(),
      entity.getRead(),
      entity.getCarId().toString()
  );

  public static NotificationEntity mapToEntity(CreateNotificationDTO dto) {
    return new NotificationEntity(
        null,
        null,
        dto.getText(),
        false,
        dto.getUsername(),
        dto.getCarId()
    );
  }
}
