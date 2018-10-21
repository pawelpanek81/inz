package pl.mycar.mapservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycar.mapservice.mapper.PointTypeMapper;
import pl.mycar.mapservice.model.dto.type.ReadPointTypeDTO;
import pl.mycar.mapservice.persistence.repository.PointTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointTypeServiceImpl implements PointTypeService {
  private PointTypeRepository pointTypeRepository;

  @Autowired
  public PointTypeServiceImpl(PointTypeRepository pointTypeRepository) {
    this.pointTypeRepository = pointTypeRepository;
  }

  @Override
  public List<ReadPointTypeDTO> readAllPointTypes() {
    return pointTypeRepository.findAll().stream()
        .map(PointTypeMapper.toDTOMapper)
        .collect(Collectors.toList());
  }
}
