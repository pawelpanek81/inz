package pl.mycar.notificationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.mycar.notificationservice.model.dto.CreateNotificationDTO;
import pl.mycar.notificationservice.model.dto.ReadNotificationDTO;
import pl.mycar.notificationservice.model.dto.UnreadNotificationsDTO;

import java.security.Principal;

public interface NotificationService {

  Page<ReadNotificationDTO> readAllNotificationsByPrincipal(Principal principal, Pageable pageable);

  UnreadNotificationsDTO readFirst5UnreadNotificationsByPrincipal(Principal principal);

  ReadNotificationDTO addNotification(CreateNotificationDTO dto);

  void readNotification(Long id, Principal principal);
}
