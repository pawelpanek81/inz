package pl.mycar.mapservice.model.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadRatingCommentDTO {
  private Long id;
  private String addedBy;
  private String comment;
  private LocalDateTime addedAt;
}
