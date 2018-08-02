package pl.mycar.accountservice.service;


import pl.mycar.accountservice.persistence.entity.AccountEntity;

import java.util.Optional;

public interface AccountService {
  AccountEntity create(AccountEntity accountEntity);

  Optional<AccountEntity> readById(Integer id);
}
