package pl.mycar.insuranceservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.insuranceservice.persistence.entity.InsuranceDocumentEntity;

import java.util.List;

public interface InsuranceDocumentRepository extends JpaRepository<InsuranceDocumentEntity, Long> {
  List<InsuranceDocumentEntity> findAllByInsuranceId(Long id);
}
