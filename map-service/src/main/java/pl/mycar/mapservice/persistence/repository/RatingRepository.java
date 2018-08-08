package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.mapservice.persistence.entity.RatingEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
}
