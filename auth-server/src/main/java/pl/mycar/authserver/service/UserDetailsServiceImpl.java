package pl.mycar.authserver.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mycar.accountservice.persistence.entity.AccountEntity;
import pl.mycar.accountservice.persistence.repository.AccountRepository;
import pl.mycar.accountservice.persistence.repository.AuthorityRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private AccountRepository accountRepository;
  private AuthorityRepository authorityRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public UserDetailsServiceImpl(AccountRepository accountRepository,
                                AuthorityRepository authorityRepository,
                                BCryptPasswordEncoder encoder) {
    this.accountRepository = accountRepository;
    this.authorityRepository = authorityRepository;
    this.bCryptPasswordEncoder = encoder;
  }

  @AllArgsConstructor
  @Data
  private static class AppUser {
    private Integer id;
    private String username, password;
    private String role;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<AccountEntity> all = accountRepository.findAll();
    System.out.println(all);

    final List<AppUser> users = Arrays.asList(
        new AppUser(1, "pawel", bCryptPasswordEncoder.encode("p"), "USER"),
        new AppUser(2, "admin", bCryptPasswordEncoder.encode("a"), "ADMIN")
    );

    for (AppUser u : users) {
      if (u.getUsername().equals(username)) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
            .commaSeparatedStringToAuthorityList("ROLE_" + u.getRole());

        return new User(u.getUsername(), u.getPassword(), grantedAuthorities);
      }
    }
    throw new UsernameNotFoundException("Username: " + username + " not found");
  }
}
