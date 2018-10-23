package pl.mycar.technicalexaminationservice.feign;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CarFallback implements CarClient {

  @Override
  public ResponseEntity<ReadCarDTO> getCar(Long id) {
    return ResponseEntity.ok(new ReadCarDTO(1L, "Twoje", "auto"));
  }
}
