package pl.mycar.carservice.service.service;

import org.springframework.web.multipart.MultipartFile;
import pl.mycar.carservice.model.dto.CreateServiceDTO;

import java.security.Principal;
import java.util.List;

public interface ServiceService {

  Long createService(CreateServiceDTO dto, Principal principal);

  void validateFiles(List<MultipartFile> files);

}
