package pl.mycar.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycar.accountservice.persistence.entity.AccountEntity;
import pl.mycar.accountservice.persistence.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
  private AccountRepository accountRepository;

  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public AccountEntity create(AccountEntity accountEntity) {
    return accountRepository.save(accountEntity);
  }

  @Override
  public Optional<AccountEntity> readById(Integer id) {
    return accountRepository.findById(id);
  }
}
