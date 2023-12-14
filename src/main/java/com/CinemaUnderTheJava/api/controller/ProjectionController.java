package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.projection.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.projection.ProjectionResponseDto;
import com.cinemaUnderTheJava.api.dto.seat.AvailableSeatsDto;
import com.cinemaUnderTheJava.business.ProjectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.cinemaUnderTheJava.api.controller.ProjectionController.ControllerRoutes.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ROOT)
public class ProjectionController {

    private final ProjectionService projectionService;

    @PostMapping(SAVE)
    public ResponseEntity<ProjectionResponseDto> saveProjection(
            @RequestBody ProjectionRequestDto projectionRequestDto,
            @PathVariable Long filmId
    ) {
        return new ResponseEntity<>(projectionService.saveProjection(projectionRequestDto, filmId), HttpStatus.CREATED);
    }

    @GetMapping(FIND_BY_DATE)
    public ResponseEntity<List<ProjectionResponseDto>> getProjectionsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<ProjectionResponseDto> result = projectionService.getProjectionsByDate(date);
        return ResponseEntity.ok(result);
    }

    @GetMapping(ALL)
    public ResponseEntity<List<ProjectionResponseDto>> findAll() {
        List<ProjectionResponseDto> result = projectionService.getAllProjections();
        return ResponseEntity.ok(result);
    }

    @GetMapping(ID)
    public ResponseEntity<AvailableSeatsDto> getAvailableSeats(
            @PathVariable Long id
    ) {
        AvailableSeatsDto availableSeats = projectionService.getAvailableSeats(id);
        return ResponseEntity.ok(availableSeats);
    }

    static final class ControllerRoutes {
        static final String ROOT = "/projections";
        static final String FIND_BY_DATE = ROOT;
        static final String SAVE = "/{filmId}";
        static final String ALL = "/all";
        static final String ID = "/id";
    }
}
