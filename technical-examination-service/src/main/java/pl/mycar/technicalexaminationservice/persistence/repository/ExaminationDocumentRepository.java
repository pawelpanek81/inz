package pl.mycar.technicalexaminationservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationDocumentEntity;

import java.util.List;

@Repository
public interface ExaminationDocumentRepository extends JpaRepository<ExaminationDocumentEntity, Long> {
  List<ExaminationDocumentEntity> findAllByExaminationId(Long id);
}
