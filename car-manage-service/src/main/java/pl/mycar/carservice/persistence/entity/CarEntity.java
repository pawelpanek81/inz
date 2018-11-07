package pl.mycar.carservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.mycar.carservice.model.database.FuelType;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarEntity {

  @Id
  @SequenceGenerator(name = "car_generator",
      sequenceName = "car_id_seq", initialValue = 1)
  @GeneratedValue(generator = "car_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "owner", nullable = false)
  private String owner;

  @Column(name = "brand", nullable = false)
  private String brand;

  @Column(name = "model", nullable = false)
  private String model;

  @Column(name = "fuel_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private FuelType fuelType;

  @Column(name = "engine_capacity", nullable = false)
  private Integer engineCapacity;

  @Column(name = "engine_power_hp", nullable = false)
  private Double enginePowerInHP;

  @Column(name = "production_year", nullable = false)
  private Integer productionYear;

  @Column(name = "vin", nullable = false)
  private String vin;
}
