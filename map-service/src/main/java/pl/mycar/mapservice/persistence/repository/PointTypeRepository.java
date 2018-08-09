package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.mapservice.persistence.entity.PointTypeEntity;

import java.util.Optional;

@Repository
public interface PointTypeRepository extends JpaRepository<PointTypeEntity, Long> {
  Optional<PointTypeEntity> findByType(String type);
}
