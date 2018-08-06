package pl.mycar.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mycar.accountservice.persistence.entity.AccountEntity;
import pl.mycar.accountservice.persistence.repository.AccountRepository;
import pl.mycar.accountservice.persistence.repository.AuthorityRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private AccountRepository accountRepository;
  private AuthorityRepository authorityRepository;

  @Autowired
  public UserDetailsServiceImpl(AccountRepository accountRepository,
                                AuthorityRepository authorityRepository) {
    this.accountRepository = accountRepository;
    this.authorityRepository = authorityRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<AccountEntity> account = accountRepository.findByUsername(username);
    AccountEntity entity = account.orElseThrow(() -> new UsernameNotFoundException(username));

    List<SimpleGrantedAuthority> authorities = authorityRepository.findByAccountId(entity.getId())
        .stream()
        .map(e -> new SimpleGrantedAuthority(e.getAuthority()))
        .collect(Collectors.toList());

    return new User(
        entity.getUsername(),
        entity.getPassword(),
        true,
        true,
        true,
        true,
        authorities
    );

  }
}
