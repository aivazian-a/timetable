package com.example.timetable.controller;

import com.example.timetable.entity.PassengerEntity;
import com.example.timetable.service.passenger.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/passengers")
@RequiredArgsConstructor
public class PassengerContoller {
    private final PassengerService passengerService;

    @GetMapping
    public List<PassengerEntity> getPassengersByTrain(@RequestParam Long trainId) {
        return passengerService.getPassengersByTrainId(trainId);
    }

    @PostMapping
    public PassengerEntity savePassenger(@RequestBody @Valid PassengerEntity passenger) {
        return passengerService.savePassenger(passenger);
    }
}
