package pl.mycar.notificationservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.mycar.notificationservice.model.dto.CreateNotificationDTO;
import pl.mycar.notificationservice.model.dto.ReadNotificationDTO;

import java.security.Principal;
import java.util.List;

public interface NotificationService {

  Page<ReadNotificationDTO> readAllNotificationsByPrincipal(Principal principal, Pageable pageable);

  List<ReadNotificationDTO> readFirst5UnreadNotificationsByPrincipal(Principal principal);

  ReadNotificationDTO addNotification(CreateNotificationDTO dto);

  void readNotification(Long id, Principal principal);
}
