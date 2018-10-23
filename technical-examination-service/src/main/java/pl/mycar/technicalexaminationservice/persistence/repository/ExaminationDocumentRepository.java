package pl.mycar.technicalexaminationservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationDocumentEntity;

public interface ExaminationDocumentRepository extends JpaRepository<ExaminationDocumentEntity, Long> {
}
