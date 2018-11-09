package pl.mycar.insuranceservice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InsuranceDocumentEntity {
  @Id
  @SequenceGenerator(name = "insurance_document_generator",
      sequenceName = "insurance_document_id_seq", initialValue = 1)
  @GeneratedValue(generator = "insurance_document_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "insurance_id", nullable = false)
  private InsuranceEntity insurance;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Lob
  @Column(name = "content", nullable = false)
  private byte[] content;
}
