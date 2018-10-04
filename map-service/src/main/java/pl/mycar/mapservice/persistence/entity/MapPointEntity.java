package pl.mycar.mapservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "map_points")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MapPointEntity {

  @Id
  @SequenceGenerator(name = "map_point_generator",
      sequenceName = "map_point_seq")
  @GeneratedValue(generator = "map_point_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @Column(name = "added_by", nullable = false)
  private String addedBy;

  @Column(name = "company_name", nullable = false)
  private String companyName;

  @Column(name = "info")
  private String info;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "zip_code", nullable = false)
  private String zipCode;;

  @Column(name = "www")
  private String www;

  @Column(name = "phone")
  private String phone;

  @Column(name = "latitude", nullable = false)
  private String latitude;

  @Column(name = "longitude", nullable = false)
  private String longitude;

  @Column(name = "added_at")
  private LocalDateTime addedAt;

  @ManyToOne(optional = false)
  @JoinColumn(name = "point_type_id")
  private PointTypeEntity pointType;

  @Column(name = "approved")
  private Boolean approved;
}