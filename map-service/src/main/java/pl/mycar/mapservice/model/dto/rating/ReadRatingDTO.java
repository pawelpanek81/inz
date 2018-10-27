package pl.mycar.mapservice.model.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadRatingDTO {
  private Long id;
  private String addedBy;
  private String header;
  private String comment;
  private Integer rating;
  private LocalDateTime addedAt;
}
