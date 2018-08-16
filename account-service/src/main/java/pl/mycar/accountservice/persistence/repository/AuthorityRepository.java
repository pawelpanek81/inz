package pl.mycar.accountservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.accountservice.persistence.entity.AuthorityEntity;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Long> {
  List<AuthorityEntity> findByAccountId(Long id);
}
