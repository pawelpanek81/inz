package pl.mycar.mapservice.model.dto.point;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
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
