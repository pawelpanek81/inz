package pl.mycar.technicalexaminationservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationEntity;

import java.util.List;

public interface ExaminationRepository extends JpaRepository<ExaminationEntity, Long> {
  List<ExaminationEntity> findAllByUsername(String username);
}
