package pl.mycar.carservice.service.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.mycar.carservice.exception.CarNotFoundException;
import pl.mycar.carservice.exception.InvalidFilesException;
import pl.mycar.carservice.exception.UnauthorizedException;
import pl.mycar.carservice.mapper.ServiceMapper;
import pl.mycar.carservice.model.dto.CreateServiceDTO;
import pl.mycar.carservice.persistence.entity.CarEntity;
import pl.mycar.carservice.persistence.entity.ServiceEntity;
import pl.mycar.carservice.persistence.repository.CarRepository;
import pl.mycar.carservice.persistence.repository.ServiceRepository;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {
  private static final List<String> ALLOWED_EXTENSION_LIST = Arrays.asList("pdf", "jpg", "jpeg", "gif", "png", "bmp");
  private static final int ALLOWED_MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024;
  private static final int ALLOWED_MAX_FILES_COUNT = 5;
  private ServiceRepository serviceRepository;
  private CarRepository carRepository;

  @Autowired
  public ServiceServiceImpl(ServiceRepository serviceRepository, CarRepository carRepository) {
    this.serviceRepository = serviceRepository;
    this.carRepository = carRepository;
  }

  @Override
  public Long createService(CreateServiceDTO dto, Principal principal) {
    Optional<CarEntity> optionalOfCar = carRepository.findById(dto.getCarId());
    if (!optionalOfCar.isPresent()) {
      throw new CarNotFoundException();
    }
    CarEntity car = optionalOfCar.get();
    if (!car.getOwner().equals(principal.getName())) {
      throw new UnauthorizedException();
    }


    ServiceEntity entity = ServiceMapper.mapToEntity(dto);
    entity.setCar(car);
    ServiceEntity save = serviceRepository.save(entity);
    return save.getId();
  }

  @Override
  public void validateFiles(List<MultipartFile> files) {
    if (files.size() > ALLOWED_MAX_FILES_COUNT) {
      throw new InvalidFilesException();
    }
    for (MultipartFile file : files) {
      String extension = FilenameUtils.getExtension(file.getOriginalFilename());
      if (!ALLOWED_EXTENSION_LIST.contains(extension) || file.getSize() > ALLOWED_MAX_FILE_SIZE_BYTES) {
        throw new InvalidFilesException();
      }
    }
  }
}
