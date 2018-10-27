package pl.mycar.technicalexaminationservice.mapper;

import pl.mycar.technicalexaminationservice.model.dto.CreateExaminationDTO;
import pl.mycar.technicalexaminationservice.model.dto.ReadExaminationDTO;
import pl.mycar.technicalexaminationservice.persistence.entity.ExaminationEntity;

import java.util.function.Function;

public class ExaminationMapper {
  public static Function<ExaminationEntity, ReadExaminationDTO> toDTOMapper = entity -> new ReadExaminationDTO(
      entity.getId(),
      entity.getExaminationDate(),
      null,
      entity.getDescription()
  );

  public static ExaminationEntity mapToEntity(CreateExaminationDTO dto) {
    return new ExaminationEntity(
        null,
        null,
        dto.getExaminationDate(),
        dto.getCarId(),
        dto.getDescription()
    );
  }
}
