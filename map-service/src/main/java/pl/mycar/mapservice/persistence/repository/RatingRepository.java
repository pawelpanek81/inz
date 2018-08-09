package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.mapservice.persistence.entity.RatingEntity;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
}
