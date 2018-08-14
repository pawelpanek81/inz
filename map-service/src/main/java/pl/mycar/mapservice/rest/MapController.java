package pl.mycar.mapservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mycar.mapservice.exception.MapPointNotFoundException;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadPointDetailsDTO;
import pl.mycar.mapservice.model.dto.rating.CreateRatingDTO;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;
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

  @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
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
  ResponseEntity<ReadPointDetailsDTO> getPointDetails(@PathVariable Long id) {
    ReadPointDetailsDTO dto;
    try {
      dto = mapService.read(id);
    } catch (MapPointNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dto);
  }

  @PostMapping(value = "/{id}/ratings", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<?> addRating(@PathVariable Long id, @Valid CreateRatingDTO dto, Principal principal) {
    ReadRatingDTO readRatingDTO;
    try {
      readRatingDTO = mapService.addRating(id, dto, principal);
    } catch (MapPointNotFoundException e) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(readRatingDTO);
  }

}
