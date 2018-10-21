package pl.mycar.notificationservice.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.notificationservice.persistence.entity.NotificationEntity;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

  Page<NotificationEntity> findAllByUsername(String username, Pageable pageable);

  List<NotificationEntity> findTop5ByUsernameAndRead(String username, Boolean read);

}
