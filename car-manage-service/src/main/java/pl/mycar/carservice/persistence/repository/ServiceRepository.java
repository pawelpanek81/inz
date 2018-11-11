package pl.mycar.carservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.carservice.persistence.entity.ServiceEntity;

import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

  List<ServiceEntity> findAllByCarIdOrderByServiceDateDesc(Long carId);

}
