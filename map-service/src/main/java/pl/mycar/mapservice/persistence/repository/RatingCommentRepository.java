package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.mapservice.persistence.entity.RatingCommentEntity;

import java.util.List;

@Repository
public interface RatingCommentRepository extends JpaRepository<RatingCommentEntity, Long> {
  List<RatingCommentEntity> findByParentId(Long id);
}
