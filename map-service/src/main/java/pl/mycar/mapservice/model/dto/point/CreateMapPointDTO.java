package pl.mycar.mapservice.model.dto.point;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@Setter
public class CreateMapPointDTO {
  @NotBlank
  private String companyName;

  private String info;

  @NotBlank
  private String address;

  private String www;

  private String phone;

  @NotBlank
  private String city;

  @NotBlank
  private String latitude;

  @NotBlank
  private String longitude;

  @NotBlank
  private String type;
}
