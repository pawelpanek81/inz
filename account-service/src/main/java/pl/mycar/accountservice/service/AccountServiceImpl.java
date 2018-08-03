package pl.mycar.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycar.accountservice.exception.EmailExistsException;
import pl.mycar.accountservice.exception.UsernameExistsException;
import pl.mycar.accountservice.persistence.entity.AccountEntity;
import pl.mycar.accountservice.persistence.entity.AuthorityEntity;
import pl.mycar.accountservice.persistence.repository.AccountRepository;
import pl.mycar.accountservice.persistence.repository.AuthorityRepository;

import java.security.Principal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
  private static final String ROLE_USER = "ROLE_USER";
  private AccountRepository accountRepository;
  private AuthorityRepository authorityRepository;

  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository,
                            AuthorityRepository authorityRepository) {
    this.accountRepository = accountRepository;
    this.authorityRepository = authorityRepository;
  }

  @Override
  public AccountEntity create(AccountEntity accountEntity) {
    if (accountRepository.existsByUsername(accountEntity.getUsername())) {
      throw new UsernameExistsException();
    } else if (accountRepository.existsByEmail(accountEntity.getEmail())) {
      throw new EmailExistsException();
    }
    AccountEntity result = accountRepository.save(accountEntity);
    AuthorityEntity authority = AuthorityEntity.builder()
        .account(result)
        .authority(ROLE_USER)
        .build();
    authorityRepository.save(authority);
    return result;
  }

  @Override
  public Optional<AccountEntity> readByPrincipal(Principal principal) {
    if (principal == null) {
      return Optional.empty();
    }
    return accountRepository.findByUsername(principal.getName());
  }
}