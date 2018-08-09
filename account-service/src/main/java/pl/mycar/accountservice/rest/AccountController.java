package pl.mycar.accountservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.mycar.accountservice.exception.EmailExistsException;
import pl.mycar.accountservice.exception.UsernameExistsException;
import pl.mycar.accountservice.mapper.AccountMapper;
import pl.mycar.accountservice.model.dto.CreateAccountDTO;
import pl.mycar.accountservice.model.dto.ReadAccountDTO;
import pl.mycar.accountservice.persistence.entity.AccountEntity;
import pl.mycar.accountservice.service.AccountService;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class AccountController {
  private AccountService accountService;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public AccountController(AccountService accountService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.accountService = accountService;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<?> signUp(@RequestBody @Valid CreateAccountDTO dto) {

    AccountEntity entity = AccountMapper.mapCreateUserDTOToAccountEntity(dto, bCryptPasswordEncoder);
    AccountEntity result;
    try {
      result = accountService.create(entity);

    } catch (UsernameExistsException | EmailExistsException e) {
      return ResponseEntity
          .status(HttpStatus.CONFLICT)
          .body("Username or email taken.");
    }

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(result.getId()).toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ReadAccountDTO> accountInfo(Principal principal) {
    Optional<AccountEntity> optionalOfAccountEntity = accountService.readByPrincipal(principal);

    if (!optionalOfAccountEntity.isPresent()) {
      return ResponseEntity.notFound().build();
    }

    ReadAccountDTO dto = AccountMapper.mapAccountEntityToReadAccountDTO(optionalOfAccountEntity.get());

    return ResponseEntity.ok(dto);
  }
}