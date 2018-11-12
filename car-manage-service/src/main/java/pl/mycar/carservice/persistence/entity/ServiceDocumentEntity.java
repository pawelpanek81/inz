package pl.mycar.carservice.persistence.entity;

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
public class ServiceDocumentEntity {

  @Id
  @SequenceGenerator(name = "service_document_generator",
      sequenceName = "service_document_id_seq", initialValue = 1)
  @GeneratedValue(generator = "service_document_generator")
  @Column(name = "id", nullable = false, unique = true)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "service_id", nullable = false)
  private ServiceEntity service;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Lob
  @Column(name = "content", nullable = false)
  private byte[] content;
}
