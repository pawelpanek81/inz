package pl.mycar.notificationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnreadNotificationsDTO {
  private List<ReadNotificationDTO> notifications;
  private Long count;
}
