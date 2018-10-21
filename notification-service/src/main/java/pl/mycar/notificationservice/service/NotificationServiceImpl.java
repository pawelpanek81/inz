package pl.mycar.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mycar.notificationservice.exception.NotificationNotFoundException;
import pl.mycar.notificationservice.exception.UnauthorizedException;
import pl.mycar.notificationservice.mapper.NotificationMapper;
import pl.mycar.notificationservice.model.dto.CreateNotificationDTO;
import pl.mycar.notificationservice.model.dto.ReadNotificationDTO;
import pl.mycar.notificationservice.persistence.entity.NotificationEntity;
import pl.mycar.notificationservice.persistence.repository.NotificationRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
  private NotificationRepository notificationRepository;

  @Autowired
  public NotificationServiceImpl(NotificationRepository notificationRepository) {
    this.notificationRepository = notificationRepository;
  }

  @Override
  public Page<ReadNotificationDTO> readAllNotificationsByPrincipal(Principal principal, Pageable pageable) {
    String username = principal.getName();
    return notificationRepository.findAllByUsername(username, pageable)
    .map(NotificationMapper.toDTOMapper);
  }

  @Override
  public List<ReadNotificationDTO> readFirst5UnreadNotificationsByPrincipal(Principal principal) {
    String username = principal.getName();
    List<NotificationEntity> top5ByUsernameAndRead = notificationRepository.findTop5ByUsernameAndRead(username, false);

    List<ReadNotificationDTO> notificationDTOS = top5ByUsernameAndRead.stream()
        .map(NotificationMapper.toDTOMapper)
        .collect(Collectors.toList());

    notificationDTOS.forEach(dto -> dto.setCar("Call to Car Microservice"));

    return notificationDTOS;
  }

  @Override
  public ReadNotificationDTO addNotification(CreateNotificationDTO dto) {
    NotificationEntity notificationEntity = NotificationMapper.mapToEntity(dto);
    notificationEntity.setAddedAt(LocalDateTime.now());
    NotificationEntity saved = notificationRepository.save(notificationEntity);
    return NotificationMapper.toDTOMapper.apply(saved);
  }

  @Override
  public void readNotification(Long id, Principal principal) {
    Optional<NotificationEntity> optionalNotificationEntity = notificationRepository.findById(id);

    if (!optionalNotificationEntity.isPresent()) {
      throw new NotificationNotFoundException();
    }

    NotificationEntity notification = optionalNotificationEntity.get();

    if (!notification.getUsername().equals(principal.getName())) {
      throw new UnauthorizedException();
    }

    notification.setRead(true);

    notificationRepository.save(notification);
  }
}
