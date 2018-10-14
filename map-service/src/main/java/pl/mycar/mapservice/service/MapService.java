package pl.mycar.mapservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.mycar.mapservice.model.dto.comment.CreateCommentDTO;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadPointDetailsDTO;
import pl.mycar.mapservice.model.dto.rating.CreateRatingDTO;
import pl.mycar.mapservice.model.dto.comment.ReadCommentDTO;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;

import java.security.Principal;
import java.util.List;

public interface MapService {

  ReadMapPointDTO createMapPoint(CreateMapPointDTO dto, Principal principal);

  List<ReadMapPointDTO> readAllMapPoints();

  ReadPointDetailsDTO readMapPoint(Long mapPointId, Pageable pageable);

  Page<ReadRatingDTO> readRatings(Long mapPointId, Pageable pageable);

  ReadRatingDTO readRatingByPrincipal(Long mapPointId, Principal principal);

  ReadRatingDTO addRating(Long mapPointId, CreateRatingDTO dto, Principal principal);

  ReadRatingDTO updateRating(Long mapPointId, Long ratingId, CreateRatingDTO dto, Principal principal);

  Boolean mapPointExists(Long mapPointId);

  Page<ReadCommentDTO> readComments(Long mapPointId, Long ratingId, Pageable pageable);

  ReadCommentDTO addComment(Long mapPointId, Long ratingId, CreateCommentDTO dto, Principal principal);

  ReadRatingDTO deleteRating(Long mapPointId, Long ratingId, Principal principal);
}
