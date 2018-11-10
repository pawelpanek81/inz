package pl.mycar.insuranceservice.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycar.insuranceservice.model.database.InsuranceType;
import pl.mycar.insuranceservice.persistence.entity.InsuranceEntity;

import java.util.List;
import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<InsuranceEntity, Long> {

  Optional<InsuranceEntity> findByIdAndUsername(Long insuranceId, String username);

  Page<InsuranceEntity> findAllByCarIdAndUsername(Long carId, String username, Pageable pageable);

  Optional<InsuranceEntity> findTop1ByCarIdAndUsernameAndTypeOrderByToDateDescId(Long carId, String username, InsuranceType type);

  List<InsuranceEntity> findAllByCarIdAndUsernameAndType(Long carId, String username, InsuranceType type);

}
