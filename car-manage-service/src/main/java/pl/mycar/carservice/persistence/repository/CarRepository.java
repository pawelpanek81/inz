package pl.mycar.carservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.carservice.persistence.entity.CarEntity;

import java.util.List;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
  List<CarEntity> findAllByOwner(String owner);

  Boolean existsByOwnerAndId(String owner, Long id);
}
