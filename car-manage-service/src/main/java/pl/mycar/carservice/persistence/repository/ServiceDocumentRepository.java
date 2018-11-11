package pl.mycar.carservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.carservice.persistence.entity.ServiceDocumentEntity;

import java.util.List;

public interface ServiceDocumentRepository extends JpaRepository<ServiceDocumentEntity, Long> {

  List<ServiceDocumentEntity> findAllByServiceId(Long serviceId);

}
