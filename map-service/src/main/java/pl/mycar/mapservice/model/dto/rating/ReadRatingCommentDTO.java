package pl.mycar.mapservice.model.dto.rating;

import java.time.LocalDateTime;

public class ReadRatingCommentDTO {
  private Long id;
  private String addedBy;
  private String comment;
  private LocalDateTime addedAt;
}
