package com.example.timetable.controller;

import com.example.timetable.dto.TrainDto;
import com.example.timetable.entity.Train;
import com.example.timetable.service.train.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @PostMapping("/save")
    public ResponseEntity saveTrain(@RequestBody TrainDto trainDto) {
        trainService.saveTrain(trainDto.toTrain());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public List<Train> getTrains() {
        return trainService.getTrains();
    }
}
