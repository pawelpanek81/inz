package pl.mycar.notificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mycar.notificationservice.exception.CarNotFoundException;
import pl.mycar.notificationservice.exception.NotificationNotFoundException;
import pl.mycar.notificationservice.exception.UnauthorizedException;
import pl.mycar.notificationservice.feign.CarClient;
import pl.mycar.notificationservice.feign.ReadCarDTO;
import pl.mycar.notificationservice.mapper.NotificationMapper;
import pl.mycar.notificationservice.model.dto.CreateNotificationDTO;
import pl.mycar.notificationservice.model.dto.ReadNotificationDTO;
import pl.mycar.notificationservice.model.dto.UnreadNotificationsDTO;
import pl.mycar.notificationservice.persistence.entity.NotificationEntity;
import pl.mycar.notificationservice.persistence.repository.NotificationRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
  private NotificationRepository notificationRepository;
  private CarClient carClient;

  @Autowired
  public NotificationServiceImpl(NotificationRepository notificationRepository, CarClient carClient) {
    this.notificationRepository = notificationRepository;
    this.carClient = carClient;
  }

  @Override
  public Page<ReadNotificationDTO> readAllNotificationsByPrincipal(Principal principal, Pageable pageable) {
    String username = principal.getName();
    Page<ReadNotificationDTO> pageOfNotifications = notificationRepository.findAllByUsername(username, pageable)
        .map(NotificationMapper.toDTOMapper);
    pageOfNotifications.forEach(dto -> dto.setCar(getCarInfo(Long.valueOf(dto.getCar()))));

    return pageOfNotifications;
  }

  @Override
  public UnreadNotificationsDTO readFirst5UnreadNotificationsByPrincipal(Principal principal) {
    String username = principal.getName();
    List<NotificationEntity> top5ByUsernameAndRead = notificationRepository
        .findTop5ByUsernameAndReadOrderByAddedAtDescId(username, false);
    Long count = notificationRepository.countByUsernameAndRead(username, false);

    List<ReadNotificationDTO> notificationDTOS = top5ByUsernameAndRead.stream()
        .map(NotificationMapper.toDTOMapper)
        .collect(Collectors.toList());

    notificationDTOS.forEach(dto -> dto.setCar(getCarInfo(Long.valueOf(dto.getCar()))));

    return new UnreadNotificationsDTO(notificationDTOS, count);
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

  private String getCarInfo(Long carId) {
    ResponseEntity<ReadCarDTO> carEntity = carClient.getCar(carId);
    if (carEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
      throw new CarNotFoundException();
    }
    ReadCarDTO body = Objects.requireNonNull(carEntity.getBody());
    return body.getBrand() + " " + body.getModel();
  }
}
