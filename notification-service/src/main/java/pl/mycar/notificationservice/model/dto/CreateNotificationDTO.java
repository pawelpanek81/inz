package pl.mycar.notificationservice.model.dto;

import lombok.Data;

@Data
public class CreateNotificationDTO {
  private Long carId;
  private String username;
  private String text;
}
