package pl.mycar.technicalexaminationservice.persistence.entity;

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
public class ExaminationDocumentEntity {

  @Id
  @SequenceGenerator(name = "examination_document_generator",
      sequenceName = "examination_document_id_seq", initialValue = 1)
  @GeneratedValue(generator = "examination_document_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "examination_id", nullable = false)
  private ExaminationEntity examination;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  //@Lob
  //@Column(name = "content", columnDefinition = "BLOB", nullable = false)
  @Column(name = "content", nullable = false, length = 5 * 1024 * 1024)
  private byte[] content;
}
