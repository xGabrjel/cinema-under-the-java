package com.cinemaUnderTheJava.api.controller;

import com.cinemaUnderTheJava.api.dto.ProjectionRequestDto;
import com.cinemaUnderTheJava.api.dto.ProjectionResponseDto;
import com.cinemaUnderTheJava.business.ProjectionService;
import com.cinemaUnderTheJava.database.entity.ProjectionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projections")
public class ProjectionController {

    private final ProjectionService projectionService;

    @PostMapping("/add")
    public ResponseEntity<ProjectionEntity> saveProjection(
            @RequestBody ProjectionRequestDto projectionRequestDto) {
        return new ResponseEntity<>(projectionService.saveProjection(projectionRequestDto), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<ProjectionResponseDto>> getProjectionsByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ProjectionResponseDto> result = projectionService.getProjectionsByDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectionResponseDto>> findAll() {
        List<ProjectionResponseDto> result = projectionService.findAllProjections();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
