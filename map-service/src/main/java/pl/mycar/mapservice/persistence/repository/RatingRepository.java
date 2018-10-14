package pl.mycar.mapservice.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.mapservice.persistence.entity.RatingEntity;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
  List<RatingEntity> findByMapPointId(Long id);
  Page<RatingEntity> findByMapPointId(Long id, Pageable pageable);
  Long countByMapPointId(Long id);
  List<RatingEntity> findByAddedByAndMapPointId(String addedBy, Long id);
}
