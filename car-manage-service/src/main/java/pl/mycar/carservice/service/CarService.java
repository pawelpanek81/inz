package pl.mycar.carservice.service;

import pl.mycar.carservice.model.dto.CreateCarDTO;
import pl.mycar.carservice.model.dto.ReadCarDTO;

import java.security.Principal;
import java.util.List;

public interface CarService {

  ReadCarDTO createCar(Principal principal, CreateCarDTO dto);

  ReadCarDTO read(Principal principal, Long carId);

  List<ReadCarDTO> readUserCars(Principal principal);

  Boolean hasUserCar(Principal principal, Long carId);
}
