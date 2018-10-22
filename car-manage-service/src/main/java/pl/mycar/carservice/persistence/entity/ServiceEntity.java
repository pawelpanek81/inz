package pl.mycar.carservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "services")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceEntity {

  @Id
  @SequenceGenerator(name = "service_generator",
      sequenceName = "service_id_seq", initialValue = 1)
  @GeneratedValue(generator = "service_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "car", nullable = false)
  private CarEntity car;

  @Column(name = "service_date", nullable = false)
  private LocalDateTime serviceDate;

  @Column(name = "mileage", nullable = false)
  private Double mileage;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "cost", nullable = false)
  private Double cost;
}
