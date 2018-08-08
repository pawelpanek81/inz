package pl.mycar.mapservice.model.dto;

import javax.validation.constraints.NotBlank;

public class CreateMapPointDTO {
  @NotBlank
  private String companyName;

  private String info;

  @NotBlank
  private String address;

  @NotBlank
  private String city;

  @NotBlank
  private String latitude;

  @NotBlank
  private String longitude;
}
