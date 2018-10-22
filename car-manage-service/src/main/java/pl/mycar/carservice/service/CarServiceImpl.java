package pl.mycar.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mycar.carservice.exception.CarNotFoundException;
import pl.mycar.carservice.exception.UnauthorizedException;
import pl.mycar.carservice.mapper.CarMapper;
import pl.mycar.carservice.model.dto.CreateCarDTO;
import pl.mycar.carservice.model.dto.ReadCarDTO;
import pl.mycar.carservice.persistence.entity.CarEntity;
import pl.mycar.carservice.persistence.repository.CarRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
  private CarRepository carRepository;

  @Autowired
  public CarServiceImpl(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @Override
  public ReadCarDTO createCar(Principal principal, CreateCarDTO dto) {
    CarEntity carEntity = CarMapper.mapToEntity(dto);
    carEntity.setOwner(principal.getName());
    carRepository.save(carEntity);
    return CarMapper.toDTOMapper.apply(carEntity);
  }

  @Override
  public ReadCarDTO read(Principal principal, Long carId) {
    Optional<CarEntity> optionalCarEntity = carRepository.findById(carId);

    if (!optionalCarEntity.isPresent()) {
      throw new CarNotFoundException();
    }

    CarEntity carEntity = optionalCarEntity.get();

    if (!carEntity.getOwner().equals(principal.getName())) {
      throw new UnauthorizedException();
    }

    return CarMapper.toDTOMapper.apply(carEntity);
  }

  @Override
  public List<ReadCarDTO> readUserCars(Principal principal) {
    String owner = principal.getName();
    List<CarEntity> allByOwner = carRepository.findAllByOwner(owner);

    return allByOwner.stream()
        .map(CarMapper.toDTOMapper)
        .collect(Collectors.toList());
  }

  @Override
  public Boolean hasUserCar(Principal principal, Long carId) {
    return carRepository.existsByOwnerAndId(principal.getName(), carId);
  }
}
