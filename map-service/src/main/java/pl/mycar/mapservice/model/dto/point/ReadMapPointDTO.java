package pl.mycar.mapservice.model.dto.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.mapservice.model.dto.type.ReadPointTypeDTO;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReadMapPointDTO {
  private Long id;
  private String addedBy;
  private String companyName;
  private String info;
  private String address;
  private String city;
  private String www;
  private String phone;
  private String latitude;
  private String longitude;
  private LocalDateTime addedAt;
  private ReadPointTypeDTO type;
}