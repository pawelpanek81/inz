package pl.mycar.mapservice.service;

import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;

import java.security.Principal;
import java.util.List;

public interface MapService {

  ReadMapPointDTO create(CreateMapPointDTO entity, Principal principal);

  List<ReadMapPointDTO> readAll();
}
