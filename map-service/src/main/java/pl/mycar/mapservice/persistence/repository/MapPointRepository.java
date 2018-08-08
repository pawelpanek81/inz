package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;

public interface MapPointRepository extends JpaRepository<MapPointEntity, Long> {
}
