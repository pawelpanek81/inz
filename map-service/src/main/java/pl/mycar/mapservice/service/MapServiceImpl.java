package pl.mycar.mapservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycar.mapservice.mapper.MapPointMapper;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;
import pl.mycar.mapservice.persistence.repository.MapPointRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapServiceImpl implements MapService {
  private MapPointRepository mapPointRepository;
  private MapPointMapper mapPointMapper;

  @Autowired
  public MapServiceImpl(MapPointRepository mapPointRepository,
                        MapPointMapper mapPointMapper) {
    this.mapPointRepository = mapPointRepository;
    this.mapPointMapper = mapPointMapper;
  }

  @Override
  public ReadMapPointDTO create(CreateMapPointDTO dto, Principal principal) {
    MapPointEntity entity = mapPointMapper.mapToEntity(dto);
    entity.setAddedBy(principal.getName());
    entity.setAddedAt(LocalDateTime.now());
    MapPointEntity savedEntity = mapPointRepository.save(entity);
    return MapPointMapper.toDTOMapper.apply(savedEntity);
  }

  @Override
  public List<ReadMapPointDTO> readAll() {
    List<MapPointEntity> mapPointEntities = mapPointRepository.findAll();
    return mapPointEntities.stream()
        .map(MapPointMapper.toDTOMapper)
        .collect(Collectors.toList());
  }
}