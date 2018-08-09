package pl.mycar.mapservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "point_types")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointTypeEntity {
  @Id
  @SequenceGenerator(name = "point_type_generator",
      sequenceName = "point_type_seq")
  @GeneratedValue(generator = "point_type_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "type", nullable = false)
  private String type;

  @Column(name = "type", nullable = false)
  private String description;

  @Column(name = "type", nullable = false)
  private String iconFile;
}
