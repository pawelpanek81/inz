package pl.mycar.carservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.mycar.carservice.exception.CarNotFoundException;
import pl.mycar.carservice.exception.UnauthorizedException;
import pl.mycar.carservice.model.dto.CreateCarDTO;
import pl.mycar.carservice.model.dto.ReadCarDTO;
import pl.mycar.carservice.service.CarService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {
  private CarService carService;

  @Autowired
  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/")
  @Secured("ROLE_USER")
  ResponseEntity<List<ReadCarDTO>> getAllCars(Principal principal) {
    List<ReadCarDTO> userCarDTOS = carService.readUserCars(principal);
    return ResponseEntity.ok(userCarDTOS);
  }

  @GetMapping("/{id}")
  @Secured("ROLE_USER")
  ResponseEntity<ReadCarDTO> getCar(Principal principal, @PathVariable Long id) {
    ReadCarDTO carDTO;
    try {
      carDTO = carService.read(principal, id);
    } catch (CarNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    return ResponseEntity.ok(carDTO);
  }

  @PostMapping("/")
  @Secured("ROLE_USER")
  ResponseEntity<ReadCarDTO> addCar(Principal principal, @RequestBody CreateCarDTO dto) {
    ReadCarDTO createdCarDTO = carService.createCar(principal, dto);
    return ResponseEntity.ok(createdCarDTO);
  }
}
