package pl.mycar.notificationservice.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.mycar.notificationservice.exception.NotificationNotFoundException;
import pl.mycar.notificationservice.exception.UnauthorizedException;
import pl.mycar.notificationservice.model.dto.ReadNotificationDTO;
import pl.mycar.notificationservice.model.dto.UnreadNotificationsDTO;
import pl.mycar.notificationservice.service.NotificationService;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(value = "")
public class NotificationController {
  private NotificationService notificationService;

  public NotificationController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @GetMapping(value = "/notifications", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_USER")
  ResponseEntity<Page<ReadNotificationDTO>> readAllNotifications(Principal principal, Pageable pageable) {
    Page<ReadNotificationDTO> allNotificationDTOS =
        notificationService.readAllNotificationsByPrincipal(principal, pageable);

    return ResponseEntity.ok(allNotificationDTOS);
  }

  @GetMapping(value = "/unread-notifications", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_USER")
  ResponseEntity<UnreadNotificationsDTO> readFirst5UnreadNotifications(Principal principal) {
    UnreadNotificationsDTO unreadNotificationsDTO = notificationService
        .readFirst5UnreadNotificationsByPrincipal(principal);

    return ResponseEntity.ok(unreadNotificationsDTO);
  }

  @PutMapping(value = "/notifications/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_USER")
  ResponseEntity<?> makeNotificationRead(@PathVariable Long id,
                                         @RequestBody Map<String, Boolean> data,
                                         Principal principal) {

    if (data.get("read") == null) {
      return ResponseEntity.badRequest().build();
    }

    if (data.get("read")) {
      try {
        notificationService.readNotification(id, principal);

      } catch (UnauthorizedException e) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
      } catch (NotificationNotFoundException e) {
        return ResponseEntity.notFound().build();
      }
    }

    return ResponseEntity.ok().build();
  }

}
