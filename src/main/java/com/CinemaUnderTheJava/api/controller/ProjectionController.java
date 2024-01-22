package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionResponseDto;
import com.cinemaUnderTheJava.api.dto.seat.AvailableSeatsDto;
import com.cinemaUnderTheJava.business.ProjectionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.cinemaUnderTheJava.api.controller.ProjectionController.ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class ProjectionController {

    static final String ROOT = "/projections";
    static final String SAVE = "/{filmId}";
    static final String ALL = "/all";
    static final String ID = "/{id}";

    static final String SAVE_PROJECTION_MESSAGE = "Save a new projection for a film";
    static final String GET_PROJECTION_BY_DATE_MESSAGE = "Get projections for a specific date";
    static final String GET_ALL_MESSAGE = "Get all projections";
    static final String GET_AVAILABLE_SEATS_MESSAGE = "Get available seats for a specific projection";
    private final ProjectionService projectionService;

    @PostMapping(SAVE)
    @Operation(summary = SAVE_PROJECTION_MESSAGE)
    public ResponseEntity<ProjectionResponseDto> saveProjection(
            @RequestBody ProjectionRequestDto projectionRequestDto,
            @PathVariable Long filmId
    ) {
        return new ResponseEntity<>(projectionService.saveProjection(projectionRequestDto, filmId), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = GET_PROJECTION_BY_DATE_MESSAGE)
    public ResponseEntity<List<ProjectionResponseDto>> getProjectionsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<ProjectionResponseDto> result = projectionService.getProjectionsByDate(date);
        return ResponseEntity.ok(result);
    }

    @GetMapping(ALL)
    @Operation(summary = GET_ALL_MESSAGE)
    public ResponseEntity<List<ProjectionResponseDto>> findAll() {
        List<ProjectionResponseDto> result = projectionService.getAllProjections();
        return ResponseEntity.ok(result);
    }

    @GetMapping(ID)
    @Operation(summary = GET_AVAILABLE_SEATS_MESSAGE)
    public ResponseEntity<AvailableSeatsDto> getAvailableSeats(
            @PathVariable Long id
    ) {
        AvailableSeatsDto availableSeats = projectionService.getAvailableSeats(id);
        return ResponseEntity.ok(availableSeats);
    }
}
