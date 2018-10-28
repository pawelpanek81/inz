package pl.mycar.notificationservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {

  @Id
  @SequenceGenerator(name = "notification_generator",
      sequenceName = "notification_seq", initialValue = 7)
  @GeneratedValue(generator = "notification_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "added_at",  nullable = false)
  private LocalDateTime addedAt;

  @Column(name = "header", nullable = false, length = 50)
  private String header;

  @Column(name = "text")
  private String text;

  @Column(name = "read")
  private Boolean read;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "car_id")
  private Long carId;
}
