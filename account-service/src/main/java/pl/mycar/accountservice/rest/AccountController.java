package pl.mycar.accountservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mycar.accountservice.model.dto.CreateUserDTO;
import pl.mycar.accountservice.persistence.entity.AccountEntity;
import pl.mycar.accountservice.service.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class AccountController {
  private AccountService accountService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public AccountController(AccountService accountService, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountService = accountService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping("")
  ResponseEntity<?> signUp(@RequestBody @Valid CreateUserDTO account) {
    account.setPassword(
        bCryptPasswordEncoder.encode(account.getPassword())
    );

    AccountEntity accountEntity = AccountEntity.builder()
        .username(account.getUsername())
        .name(account.getName())
        .surname(account.getSurname())
        .password(account.getPassword())
        .email(account.getEmail())
        .build();

    accountService.create(accountEntity);
    return ResponseEntity.ok().build();
  }
}
