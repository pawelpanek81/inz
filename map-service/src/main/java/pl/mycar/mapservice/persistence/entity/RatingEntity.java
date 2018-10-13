package pl.mycar.mapservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingEntity {

  @Id
  @SequenceGenerator(name = "rating_generator",
      sequenceName = "rating_seq")
  @GeneratedValue(generator = "rating_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "added_by", nullable = false)
  private String addedBy;

  @Column(name = "header", nullable = false)
  private String header;

  @Column(name = "comment", nullable = false)
  private String comment;

  @Column(name = "rating")
  private Integer rating;

  @ManyToOne(optional = false)
  @JoinColumn(name = "map_point_id")
  private MapPointEntity mapPoint;

  @Column(name = "added_at")
  private LocalDateTime addedAt;
}
