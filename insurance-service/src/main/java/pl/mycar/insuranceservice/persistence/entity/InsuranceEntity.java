package pl.mycar.insuranceservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.insuranceservice.model.database.InsuranceType;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "insurances")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceEntity {
  @Id
  @SequenceGenerator(name = "insurance_generator",
      sequenceName = "insurance_id_seq", initialValue = 2)
  @GeneratedValue(generator = "insurance_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "from_date", nullable = false)
  private LocalDate fromDate;

  @Column(name = "to_date", nullable = false)
  private LocalDate toDate;

  @Column(name = "cost")
  private Double cost;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  private InsuranceType type;

  @Column(name = "car_id", nullable = false)
  private Long carId;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "description")
  private String description;
}
