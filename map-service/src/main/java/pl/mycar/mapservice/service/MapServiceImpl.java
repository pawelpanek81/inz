package pl.mycar.mapservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;
import pl.mycar.mapservice.persistence.repository.MapPointRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MapServiceImpl implements MapService {
  private MapPointRepository mapPointRepository;

  @Autowired
  public MapServiceImpl(MapPointRepository mapPointRepository) {
    this.mapPointRepository = mapPointRepository;
  }

  @Override
  public MapPointEntity create(MapPointEntity entity, Principal principal) {
    entity.setAddedBy(principal.getName());
    entity.setAddedAt(LocalDateTime.now());
    return mapPointRepository.save(entity);
  }

  @Override
  public List<MapPointEntity> readAll() {
    return mapPointRepository.findAll();
  }
}
