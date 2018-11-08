package pl.mycar.technicalexaminationservice.rabbitmq.push.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushNotificationDTO {
  private String header;
  private String text;
  private String username;
  private Long carId;
}
