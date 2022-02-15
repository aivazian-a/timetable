package com.example.timetable.controller;

import com.example.timetable.entity.TrainEntity;
import com.example.timetable.service.train.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @PostMapping
    public TrainEntity saveTrain(@RequestBody @Valid TrainEntity train) {
        return trainService.saveTrain(train);
    }

    @GetMapping
    public List<TrainEntity> getTrains() {
        return trainService.getTrains();
    }

    @GetMapping("/find")
    public List<TrainEntity> findTrains(
            @RequestParam Long fromStation,
            @RequestParam Long toStation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    ) {
        return trainService.findTrainsByTimeAndStation(fromStation, toStation, fromDate, toDate);
    }
}
