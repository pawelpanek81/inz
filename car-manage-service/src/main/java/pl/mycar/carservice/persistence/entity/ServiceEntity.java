package pl.mycar.carservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.carservice.model.database.ServiceType;

import javax.persistence.*;
import java.time.LocalDate;

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
  private LocalDate serviceDate;

  @Column(name = "service_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private ServiceType serviceType;

  @Column(name = "mileage", nullable = false)
  private Double mileage;

  @Column(name = "header", nullable = false)
  private String header;

  @Column(name = "description", nullable = false, length = 10000)
  private String description;

  @Column(name = "cost", nullable = false)
  private Double cost;
}
