package pl.mycar.mapservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mycar.mapservice.exception.MapPointNotFoundException;
import pl.mycar.mapservice.exception.PointTypeNotFoundException;
import pl.mycar.mapservice.mapper.MapPointMapper;
import pl.mycar.mapservice.mapper.RatingCommentMapper;
import pl.mycar.mapservice.mapper.RatingMapper;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadPointDetailsDTO;
import pl.mycar.mapservice.model.dto.rating.CreateRatingDTO;
import pl.mycar.mapservice.model.dto.rating.ReadRatingCommentDTO;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;
import pl.mycar.mapservice.persistence.entity.MapPointEntity;
import pl.mycar.mapservice.persistence.entity.PointTypeEntity;
import pl.mycar.mapservice.persistence.entity.RatingCommentEntity;
import pl.mycar.mapservice.persistence.entity.RatingEntity;
import pl.mycar.mapservice.persistence.repository.MapPointRepository;
import pl.mycar.mapservice.persistence.repository.PointTypeRepository;
import pl.mycar.mapservice.persistence.repository.RatingCommentRepository;
import pl.mycar.mapservice.persistence.repository.RatingRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MapServiceImpl implements MapService {
  private MapPointRepository mapPointRepository;
  private PointTypeRepository pointTypeRepository;
  private RatingRepository ratingRepository;
  private RatingCommentRepository ratingCommentRepository;

  @Autowired
  public MapServiceImpl(MapPointRepository mapPointRepository,
                        PointTypeRepository pointTypeRepository,
                        RatingRepository ratingRepository,
                        RatingCommentRepository ratingCommentRepository) {

    this.mapPointRepository = mapPointRepository;
    this.pointTypeRepository = pointTypeRepository;
    this.ratingRepository = ratingRepository;
    this.ratingCommentRepository = ratingCommentRepository;
  }

  @Override
  public ReadMapPointDTO create(CreateMapPointDTO dto, Principal principal) {
    MapPointEntity entity = MapPointMapper.mapToEntity(dto);
    entity.setAddedBy(principal.getName());
    entity.setAddedAt(LocalDateTime.now());
    Optional<PointTypeEntity> optOfType = pointTypeRepository.findByType(dto.getType());
    if (!optOfType.isPresent()) {
      throw new PointTypeNotFoundException();
    }
    entity.setPointType(optOfType.get());

    MapPointEntity savedEntity = mapPointRepository.save(entity);
    return MapPointMapper.toDTOMapper.apply(savedEntity);
  }

  @Override
  public List<ReadMapPointDTO> readAll() {
    List<MapPointEntity> mapPointEntities = mapPointRepository.findAll();
    return mapPointEntities.stream()
        .map(MapPointMapper.toDTOMapper)
        .collect(Collectors.toList());
  }

  @Override
  public ReadPointDetailsDTO read(Long id, Pageable pageable) {
    ReadPointDetailsDTO dto = new ReadPointDetailsDTO();

    Optional<MapPointEntity> optionalOfMapPoint = mapPointRepository.findById(id);

    if (!optionalOfMapPoint.isPresent()) {
      throw new MapPointNotFoundException();
    }
    dto.setMapPoint(MapPointMapper.toDTOMapper.apply(optionalOfMapPoint.get()));

    Page<RatingEntity> mapPointRatings = ratingRepository.findByMapPointId(id, pageable);
    dto.setRatings(mapPointRatings.map(RatingMapper.toDTOMapper));

    for (ReadRatingDTO rating : dto.getRatings()) {
      List<RatingCommentEntity> ratingComments = ratingCommentRepository.findByParentId(rating.getId());

      List<ReadRatingCommentDTO> ratingCommentDTOS = ratingComments.stream()
          .map(RatingCommentMapper.toDTOMapper)
          .collect(Collectors.toList());

      rating.setSubComments(ratingCommentDTOS);
    }

    Long ratingCount = ratingRepository.countByMapPointId(id);

    dto.setRatingCount(ratingCount);

    Double ratingSum = ratingRepository.findByMapPointId(id).stream()
        .map(RatingEntity::getRating)
        .map(Double::valueOf)
        .reduce(0.0, (acc, act) -> acc + act);

    dto.setAverageRating(String.valueOf(ratingSum / ratingCount));

    return dto;
  }

  @Override
  public ReadRatingDTO addRating(Long mapPointId, CreateRatingDTO dto, Principal principal) {
    Optional<MapPointEntity> optionalOfMapPointEntity = mapPointRepository.findById(mapPointId);

    if (!optionalOfMapPointEntity.isPresent()) {
      throw new MapPointNotFoundException();
    }

    RatingEntity ratingEntity = RatingMapper.mapToEntity(dto);
    ratingEntity.setAddedAt(LocalDateTime.now());
    ratingEntity.setAddedBy(principal.getName());
    ratingEntity.setMapPoint(optionalOfMapPointEntity.get());

    RatingEntity save = ratingRepository.save(ratingEntity);

    return RatingMapper.toDTOMapper.apply(save);
  }

}