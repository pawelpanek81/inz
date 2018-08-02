package pl.mycar.accountservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
  @NotBlank
  @Size(min = 3, max = 12)
  private String username;

  @NotBlank
  @Size(min = 3, max = 12)
  private String name;

  @NotBlank
  @Size(min = 3, max = 12)
  private String surname;

  @NotBlank
  @Size(min = 4)
  private String password;

  @NotBlank
  @Email
  private String email;
}
