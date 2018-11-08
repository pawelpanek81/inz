package pl.mycar.notificationservice.rabbitmq.push;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mycar.notificationservice.persistence.entity.NotificationEntity;
import pl.mycar.notificationservice.persistence.repository.NotificationRepository;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class PushMessageReceiver {
  private NotificationRepository notificationRepository;

  @Autowired
  public PushMessageReceiver(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  private void receiveMessage(String message) {
    try {
      System.out.println("Received <" + message + ">");
      System.out.println("Sending push...");
      NotificationEntity notificationEntity = new ObjectMapper().readValue(message, NotificationEntity.class);
      notificationEntity.setAddedAt(LocalDateTime.now());
      notificationEntity.setRead(false);
      notificationRepository.save(notificationEntity);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}