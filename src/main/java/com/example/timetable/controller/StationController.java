package com.example.timetable.controller;

import com.example.timetable.entity.StationEntity;
import com.example.timetable.service.station.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class StationController {
    private final StationService stationService;

    @PostMapping
    public StationEntity saveStation(@RequestBody @Valid StationEntity station) {
        return stationService.saveStation(station);
    }

    @GetMapping
    public List<StationEntity> getStations() {
        return stationService.getStations();
    }
}
