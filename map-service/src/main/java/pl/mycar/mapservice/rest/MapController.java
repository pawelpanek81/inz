package pl.mycar.mapservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.mycar.mapservice.exception.MapPointNotFoundException;
import pl.mycar.mapservice.exception.RatingNotFoundException;
import pl.mycar.mapservice.exception.UnauthorizedException;
import pl.mycar.mapservice.exception.UserAlreadyHasRatingException;
import pl.mycar.mapservice.model.dto.comment.CreateCommentDTO;
import pl.mycar.mapservice.model.dto.comment.ReadCommentDTO;
import pl.mycar.mapservice.model.dto.point.CreateMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadMapPointDTO;
import pl.mycar.mapservice.model.dto.point.ReadPointDetailsDTO;
import pl.mycar.mapservice.model.dto.rating.CreateRatingDTO;
import pl.mycar.mapservice.model.dto.rating.ReadRatingDTO;
import pl.mycar.mapservice.service.MapService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("map-points")
class MapController {
  private MapService mapService;

  @Autowired
  public MapController(MapService mapService) {
    this.mapService = mapService;
  }

  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_USER")
  ResponseEntity<ReadMapPointDTO> addNewPoint(@Valid @RequestBody CreateMapPointDTO dto, Principal principal) {
    ReadMapPointDTO mapPoint = mapService.createMapPoint(dto, principal);
    return new ResponseEntity<>(mapPoint, HttpStatus.CREATED);
  }

  @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<List<ReadMapPointDTO>> getAllPoints() {
    List<ReadMapPointDTO> mapPointDTOS = mapService.readAllMapPoints();
    return ResponseEntity.ok(mapPointDTOS);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<ReadPointDetailsDTO> getPointDetails(@PathVariable Long id, Pageable pageable) {
    ReadPointDetailsDTO dto;
    try {
      dto = mapService.readMapPoint(id, pageable);
    } catch (MapPointNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(dto);
  }

  @GetMapping(value = "/{id}/ratings", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Page<ReadRatingDTO>> getRatingsPage(@PathVariable Long id, Pageable pageable) {
    Page<ReadRatingDTO> readRatingDTOS;
    try {
      readRatingDTOS = mapService.readRatings(id, pageable);
    } catch (MapPointNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(readRatingDTOS);
  }

  @GetMapping(value = "/{id}/my-rating", produces = MediaType.APPLICATION_JSON_VALUE)
  @Secured("ROLE_USER")
  ResponseEntity<ReadRatingDTO> getRatingByPrincipal(
      @PathVariable Long id,
      @RequestParam String username,
      Principal principal) {

    ReadRatingDTO readRatingDTO;
    try {
      readRatingDTO = mapService.readRatingByPrincipal(id, username, principal);
    } catch (UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    return ResponseEntity.ok(readRatingDTO);
  }

  @PostMapping(
      value = "/{id}/ratings",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Secured("ROLE_USER")
  ResponseEntity<ReadRatingDTO> addRating(@PathVariable Long id, @Valid @RequestBody CreateRatingDTO dto, Principal principal) {
    ReadRatingDTO readRatingDTO;
    try {
      readRatingDTO = mapService.addRating(id, dto, principal);
    } catch (MapPointNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (UserAlreadyHasRatingException e) {
      return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    return ResponseEntity.ok(readRatingDTO);
  }

  @PutMapping(
      value = "/{mapPointId}/ratings/{ratingId}",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Secured("ROLE_USER")
  ResponseEntity<ReadRatingDTO> updateRating(
      @PathVariable Long mapPointId,
      @PathVariable Long ratingId,
      @RequestBody CreateRatingDTO createRatingDTO,
      Principal principal
  ) {
    ReadRatingDTO readRatingDTO;

    try {
      readRatingDTO = mapService.updateRating(mapPointId, ratingId, createRatingDTO, principal);
    } catch (RatingNotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (UnauthorizedException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    return ResponseEntity.ok(readRatingDTO);
  }

  @GetMapping(value = "/{mapPointId}/ratings/{ratingId}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Page<ReadCommentDTO>> readCommentsPage(
      @PathVariable Long mapPointId,
      @PathVariable Long ratingId,
      Pageable pageable
  ) {
    Page<ReadCommentDTO> readRatingCommentDTOS;
    try {
      readRatingCommentDTOS = mapService.readComments(mapPointId, ratingId, pageable);

    } catch (MapPointNotFoundException | RatingNotFoundException e) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(readRatingCommentDTOS);
  }

  @PostMapping(
      value = "/{mapPointId}/ratings/{ratingId}/comments",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Secured("ROLE_USER")
  ResponseEntity<ReadCommentDTO> addComment(
      @PathVariable Long mapPointId,
      @PathVariable Long ratingId,
      @Valid @RequestBody CreateCommentDTO dto,
      Principal principal) {

    ReadCommentDTO readCommentDTO;

    try {
      readCommentDTO = mapService.addComment(mapPointId, ratingId, dto, principal);
    } catch (MapPointNotFoundException | RatingNotFoundException e) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(readCommentDTO);
  }

}
