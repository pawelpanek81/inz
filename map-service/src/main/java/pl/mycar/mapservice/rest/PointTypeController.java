package pl.mycar.mapservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mycar.mapservice.model.dto.type.ReadPointTypeDTO;
import pl.mycar.mapservice.service.PointTypeService;

import java.util.List;

@RestController
@RequestMapping("map-point-types")
class PointTypeController {
  private PointTypeService pointTypeService;

  @Autowired
  public PointTypeController(PointTypeService pointTypeService) {
    this.pointTypeService = pointTypeService;
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ReadPointTypeDTO>> readMapPointTypes() {
    List<ReadPointTypeDTO> pointTypeDTOS = pointTypeService.readAllPointTypes();
    return ResponseEntity.ok(pointTypeDTOS);
  }
}
