package com.example.timetable.controller;

import com.example.timetable.dto.StationDto;
import com.example.timetable.entity.Station;
import com.example.timetable.service.station.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @PostMapping("/save")
    public ResponseEntity saveStation(@RequestBody StationDto stationDto) {
        stationService.saveStation(stationDto.toStation());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Station> getStations() {
        return stationService.getStations();
    }
}
