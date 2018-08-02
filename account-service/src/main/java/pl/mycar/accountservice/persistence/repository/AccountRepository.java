package pl.mycar.accountservice.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mycar.accountservice.persistence.entity.AccountEntity;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
  AccountEntity findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
