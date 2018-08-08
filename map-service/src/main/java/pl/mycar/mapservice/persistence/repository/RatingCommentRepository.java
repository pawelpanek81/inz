package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.mapservice.persistence.entity.RatingCommentEntity;

public interface RatingCommentRepository extends JpaRepository<RatingCommentEntity, Long> {
}
