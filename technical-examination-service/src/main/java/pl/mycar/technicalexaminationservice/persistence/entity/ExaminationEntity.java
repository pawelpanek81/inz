package pl.mycar.technicalexaminationservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "examinations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExaminationEntity {

  @Id
  @SequenceGenerator(name = "examination_generator",
      sequenceName = "examination_id_seq", initialValue = 2)
  @GeneratedValue(generator = "examination_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "examination_date", nullable = false)
  private LocalDate examinationDate;

  @Column(name = "car_id", nullable = false)
  private Long carId;

  @Column(name = "description")
  private String description;
}
