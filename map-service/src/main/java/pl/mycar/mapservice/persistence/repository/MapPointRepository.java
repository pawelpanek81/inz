package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;

@Repository
public interface MapPointRepository extends JpaRepository<MapPointEntity, Long> {
}
