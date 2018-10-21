package pl.mycar.mapservice.service;

import pl.mycar.mapservice.model.dto.type.ReadPointTypeDTO;

import java.util.List;

public interface PointTypeService {

  List<ReadPointTypeDTO> readAllPointTypes();

}
