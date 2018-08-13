package pl.mycar.mapservice.model.dto.rating;

import java.time.LocalDateTime;
import java.util.List;

public class ReadRatingDTO {
  private Long id;
  private String addedBy;
  private String comment;
  private Double rating;
  private LocalDateTime addedAt;
  private List<ReadRatingCommentDTO> subcomments;
}
