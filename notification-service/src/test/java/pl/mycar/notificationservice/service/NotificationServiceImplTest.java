package pl.mycar.notificationservice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import pl.mycar.notificationservice.feign.CarClient;
import pl.mycar.notificationservice.feign.ReadCarDTO;
import pl.mycar.notificationservice.mapper.NotificationMapper;
import pl.mycar.notificationservice.model.dto.ReadNotificationDTO;
import pl.mycar.notificationservice.model.dto.UnreadNotificationsDTO;
import pl.mycar.notificationservice.persistence.entity.NotificationEntity;
import pl.mycar.notificationservice.persistence.repository.NotificationRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationServiceImplTest {

  @Mock
  private NotificationRepository notificationRepository;

  @Mock
  private CarClient carClient;

  @Mock
  private Principal principal;

  private NotificationService notificationService;

  @Before
  public void init() {
    notificationService = new NotificationServiceImpl(notificationRepository, carClient);
  }

  @Test
  public void readFirst5UnreadNotificationsByPrincipalTest() {
    // arrange
    String username = "user1";

    Long carId = 1L;
    String brand = "Mercedes";
    String model = "W201";

    LocalDateTime notification1LocalDateTime = LocalDateTime.now();
    LocalDateTime notification2LocalDateTime = LocalDateTime.now();
    LocalDateTime notification3LocalDateTime = LocalDateTime.now();
    LocalDateTime notification4LocalDateTime = LocalDateTime.now();

    NotificationEntity notification1 = new NotificationEntity(1L, notification1LocalDateTime, "notif1", "text1", false, username, carId);
    ReadNotificationDTO expectedNotification1 = NotificationMapper.toDTOMapper.apply(notification1);
    expectedNotification1.setCar(brand + " " + model);

    NotificationEntity notification2 = new NotificationEntity(2L, notification2LocalDateTime, "notif2", "text2", false, username, carId);
    ReadNotificationDTO expectedNotification2 = NotificationMapper.toDTOMapper.apply(notification2);
    expectedNotification2.setCar(brand + " " + model);

    NotificationEntity notification3 = new NotificationEntity(3L, notification3LocalDateTime, "notif3", "text3", true, username, carId);
    NotificationEntity notification4 = new NotificationEntity(4L, notification4LocalDateTime, "notif4", "text4", false, username, carId);
    ReadNotificationDTO expectedNotification4 = NotificationMapper.toDTOMapper.apply(notification4);
    expectedNotification4.setCar(brand + " " + model);

    List<NotificationEntity> notifications = new ArrayList<NotificationEntity>() {{
      add(notification1);
      add(notification2);
      add(notification3);
      add(notification4);
    }};

    Long expectedCount = 3L;
    when(notificationRepository.findTop5ByUsernameAndReadOrderByAddedAtDescId(username, false)).thenReturn(notifications);
    when(notificationRepository.countByUsernameAndRead(username, false)).thenReturn(expectedCount);

    when(carClient.getCar(carId)).thenReturn(ResponseEntity.ok(new ReadCarDTO(carId, brand, model)));
    when(principal.getName()).thenReturn(username);

    // act
    UnreadNotificationsDTO result = notificationService.readFirst5UnreadNotificationsByPrincipal(principal);

    // assert
    assertEquals(expectedCount, result.getCount());
    assertTrue(result.getNotifications().contains(expectedNotification1));
    assertTrue(result.getNotifications().contains(expectedNotification2));
    assertTrue(result.getNotifications().contains(expectedNotification4));
  }
}