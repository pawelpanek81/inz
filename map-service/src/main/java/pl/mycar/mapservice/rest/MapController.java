package pl.mycar.mapservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mycar.mapservice.mapper.MapPointMapper;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;
import pl.mycar.mapservice.service.MapService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("map-points")
class MapController {
  private MapService mapService;

  @Autowired
  public MapController(MapService mapService) {
    this.mapService = mapService;
  }

  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<?> addNewPoint(@Valid CreateMapPointDTO dto, Principal principal) {
    MapPointEntity entity = MapPointMapper.mapToEntity(dto);
    mapService.create(entity, principal);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ReadMapPointDTO>> getAllPoints() {
    List<ReadMapPointDTO> mapPointEntities = mapService.readAll().stream()
        .map(MapPointMapper.toDTOMapper)
        .collect(Collectors.toList());

    return ResponseEntity.ok(mapPointEntities);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<?> getPointDetails(@PathVariable Integer id) {
    return null;
  }

}
