package pl.mycar.accountservice.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mycar.accountservice.model.dto.CreateAccountDTO;
import pl.mycar.accountservice.model.dto.ReadAccountDTO;
import pl.mycar.accountservice.persistence.entity.AccountEntity;

public class AccountMapper {

  public static AccountEntity mapCreateUserDTOToAccountEntity(CreateAccountDTO dto, PasswordEncoder passwordEncoder) {
    dto.setPassword(
        passwordEncoder.encode(dto.getPassword())
    );

    return AccountEntity.builder()
        .username(dto.getUsername())
        .name(dto.getName())
        .surname(dto.getSurname())
        .password(dto.getPassword())
        .email(dto.getEmail())
        .build();
  }

  public static ReadAccountDTO mapAccountEntityToReadAccountDTO(AccountEntity entity) {
    return ReadAccountDTO.builder()
        .username(entity.getUsername())
        .name(entity.getName())
        .surname(entity.getSurname())
        .email(entity.getEmail())
        .build();
  }
}