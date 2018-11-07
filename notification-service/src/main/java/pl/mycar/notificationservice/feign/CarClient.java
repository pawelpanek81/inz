package pl.mycar.notificationservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(path = "cars", value = "car-manage-service", fallback = CarFallback.class, configuration = AuthForwardInterceptor.class)
public interface CarClient {

  @GetMapping("/{id}")
  ResponseEntity<ReadCarDTO> getCar(@PathVariable(value = "id") Long id);

}