package com.example.timetable.controller;

import com.example.timetable.entity.Passenger;
import com.example.timetable.service.passenger.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class PassengerContoller {
    private final PassengerService passengerService;

    @GetMapping
    public List<Passenger> getPassengersByTrain(@RequestParam String train) {
        return passengerService.getPassengersByTrain(train);
    }
}
