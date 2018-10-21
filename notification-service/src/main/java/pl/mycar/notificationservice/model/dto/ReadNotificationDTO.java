package pl.mycar.notificationservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadNotificationDTO {
  private Long id;
  private LocalDateTime addedAt;
  private String text;
  private Boolean read;
  private String car;
}
