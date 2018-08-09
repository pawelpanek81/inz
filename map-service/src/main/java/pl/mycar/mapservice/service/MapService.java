package pl.mycar.mapservice.service;

import pl.mycar.mapservice.persistence.entity.MapPointEntity;

import java.security.Principal;
import java.util.List;

public interface MapService {

  MapPointEntity create(MapPointEntity entity, Principal principal);

  List<MapPointEntity> readAll();
}
