package pl.mycar.mapservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadPointDetailsDTO;
import pl.mycar.mapservice.service.MapService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

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
    mapService.create(dto, principal);
    return ResponseEntity.ok().build();
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ReadMapPointDTO>> getAllPoints() {
    List<ReadMapPointDTO> mapPointDTOS = mapService.readAll();
    return ResponseEntity.ok(mapPointDTOS);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ReadPointDetailsDTO> getPointDetails(@PathVariable Integer id) {
    return null;
  }

}
