package pl.mycar.mapservice.service;

import org.springframework.data.domain.Pageable;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadPointDetailsDTO;
import pl.mycar.mapservice.model.dto.rating.CreateRatingDTO;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;

import java.security.Principal;
import java.util.List;

public interface MapService {

  ReadMapPointDTO create(CreateMapPointDTO entity, Principal principal);

  List<ReadMapPointDTO> readAll();

  ReadPointDetailsDTO read(Long id, Pageable pageable);

  ReadRatingDTO addRating(Long mapPointId, CreateRatingDTO dto, Principal principal);
}
