package pl.mycar.mapservice.model.dto.rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReadRatingDTO {
  private Long id;
  private String addedBy;
  private String comment;
  private String rating;
  private LocalDateTime addedAt;
  private List<ReadRatingCommentDTO> subComments;
}
