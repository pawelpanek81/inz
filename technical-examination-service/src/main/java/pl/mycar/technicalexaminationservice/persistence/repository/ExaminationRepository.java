package pl.mycar.technicalexaminationservice.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationEntity;

import java.util.Optional;

public interface ExaminationRepository extends JpaRepository<ExaminationEntity, Long> {
  Optional<ExaminationEntity> findByIdAndUsername(Long id, String username);
  Optional<ExaminationEntity> findTop1ByCarIdAndUsernameOrderByExaminationDateDescId(Long carId, String username);
  Page<ExaminationEntity> findAllByCarIdAndUsername(Long carId, String username, Pageable pageable);
}
