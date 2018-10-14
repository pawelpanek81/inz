package pl.mycar.mapservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mycar.mapservice.exception.*;
import pl.mycar.mapservice.mapper.MapPointMapper;
import pl.mycar.mapservice.mapper.RatingCommentMapper;
import pl.mycar.mapservice.mapper.RatingMapper;
import pl.mycar.mapservice.model.dto.comment.CreateCommentDTO;
import pl.mycar.mapservice.model.dto.comment.ReadCommentDTO;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadPointDetailsDTO;
import pl.mycar.mapservice.model.dto.rating.CreateRatingDTO;
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
  public ReadMapPointDTO createMapPoint(CreateMapPointDTO dto, Principal principal) {
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
  public List<ReadMapPointDTO> readAllMapPoints() {
    List<MapPointEntity> mapPointEntities = mapPointRepository.findAll();
    return mapPointEntities.stream()
        .map(MapPointMapper.toDTOMapper)
        .collect(Collectors.toList());
  }

  @Override
  public ReadPointDetailsDTO readMapPoint(Long mapPointId, Pageable pageable) {
    ReadPointDetailsDTO dto = new ReadPointDetailsDTO();

    Optional<MapPointEntity> optionalOfMapPoint = mapPointRepository.findById(mapPointId);

    if (!optionalOfMapPoint.isPresent()) {
      throw new MapPointNotFoundException();
    }
    dto.setMapPoint(MapPointMapper.toDTOMapper.apply(optionalOfMapPoint.get()));

    Long ratingCount = ratingRepository.countByMapPointId(mapPointId);

    dto.setRatingCount(ratingCount);

    Double ratingSum = ratingRepository.findByMapPointId(mapPointId).stream()
        .map(RatingEntity::getRating)
        .map(Double::valueOf)
        .reduce(0.0, (acc, act) -> acc + act);

    dto.setAverageRating(Math.round(ratingSum / ratingCount * 100) / 100D);

    return dto;
  }

  @Override
  public Page<ReadRatingDTO> readRatings(Long mapPointId, Pageable pageable) {
    Optional<MapPointEntity> optionalOfMapPoint = mapPointRepository.findById(mapPointId);

    if (!optionalOfMapPoint.isPresent()) {
      throw new MapPointNotFoundException();
    }

    Page<RatingEntity> mapPointRatings = ratingRepository.findByMapPointId(mapPointId, pageable);
    return mapPointRatings.map(RatingMapper.toDTOMapper);
  }

  @Override
  public ReadRatingDTO readRatingByPrincipal(Long mapPointId, Principal principal) {
    Optional<RatingEntity> anyRating = ratingRepository.findByAddedByAndMapPointId(principal.getName(), mapPointId)
        .stream()
        .findAny();

    if (anyRating.isPresent()) {
      return RatingMapper.toDTOMapper.apply(anyRating.get());
    }
    return null;
  }

  @Override
  public ReadRatingDTO addRating(Long mapPointId, CreateRatingDTO dto, Principal principal) {
    if (this.readRatingByPrincipal(mapPointId, principal) != null) {
      throw new UserAlreadyHasRatingException();
    }

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

  @Override
  public ReadRatingDTO updateRating(Long mapPointId, Long ratingId, CreateRatingDTO dto, Principal principal) {
    RatingEntity ratingEntity = ratingRepository.findById(ratingId)
        .orElseThrow(RatingNotFoundException::new);

    ReadRatingDTO readRatingDTO = this.readRatingByPrincipal(mapPointId, principal);
    if (!readRatingDTO.getId().equals(ratingId)) {
      throw new UnauthorizedException();
    }

    ratingEntity.setRating(dto.getRating());
    ratingEntity.setHeader(dto.getHeader());
    ratingEntity.setComment(dto.getComment());

    RatingEntity save = ratingRepository.save(ratingEntity);
    return RatingMapper.toDTOMapper.apply(save);
  }

  @Override
  public Boolean mapPointExists(Long mapPointId) {
    return mapPointRepository.existsById(mapPointId);
  }

  @Override
  public Page<ReadCommentDTO> readComments(Long mapPointId, Long ratingId, Pageable pageable) {
    if (!this.mapPointExists(mapPointId)) {
      throw new MapPointNotFoundException();
    }

    Optional<RatingEntity> optionalOfRating = ratingRepository.findById(ratingId);

    if (!optionalOfRating.isPresent()) {
      throw new RatingNotFoundException();
    }

    return ratingCommentRepository.findByParentId(ratingId, pageable)
        .map(RatingCommentMapper.toDTOMapper);
  }

  @Override
  public ReadCommentDTO addComment(Long mapPointId, Long ratingId, CreateCommentDTO dto, Principal principal) {
    if (!this.mapPointExists(mapPointId)) {
      throw new MapPointNotFoundException();
    }

    Optional<RatingEntity> optionalOfRating = ratingRepository.findById(ratingId);

    if (!optionalOfRating.isPresent()) {
      throw new RatingNotFoundException();
    }

    RatingCommentEntity entity = RatingCommentMapper.mapToEntity(dto);
    entity.setAddedAt(LocalDateTime.now());
    entity.setAddedBy(principal.getName());
    entity.setParent(optionalOfRating.get());

    RatingCommentEntity save = ratingCommentRepository.save(entity);

    return RatingCommentMapper.toDTOMapper.apply(save);
  }

}