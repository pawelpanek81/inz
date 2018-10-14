package pl.mycar.mapservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingCommentEntity {
  @Id
  @SequenceGenerator(name = "comment_generator",
      sequenceName = "comment_seq", initialValue = 6)
  @GeneratedValue(generator = "comment_generator")
  private Long id;

  @Column(name = "added_by", nullable = false)
  private String addedBy;

  @ManyToOne
  @JoinColumn(name = "parent")
  private RatingEntity parent;

  @Column(name = "comment", nullable = false)
  private String comment;

  @Column(name = "added_at")
  private LocalDateTime addedAt;
}
