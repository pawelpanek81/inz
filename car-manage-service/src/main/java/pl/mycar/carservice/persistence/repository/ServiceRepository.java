package pl.mycar.carservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.carservice.persistence.entity.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
}
