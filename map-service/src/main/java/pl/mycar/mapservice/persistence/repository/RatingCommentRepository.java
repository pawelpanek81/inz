package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.mapservice.persistence.entity.RatingCommentEntity;

@Repository
public interface RatingCommentRepository extends JpaRepository<RatingCommentEntity, Long> {
  Page<RatingCommentEntity> findByParentId(Long id, Pageable pageable);
}
