package pl.mycar.accountservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorityEntity {
  @Id
  @SequenceGenerator(name = "authorities_generator",
      sequenceName = "authorities_id_seq")
  @GeneratedValue(generator = "authorities_generator")
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "account", nullable = false)
  private AccountEntity account;

  @Column(name = "authority", nullable = false)
  private String authority;
}
