package com.example.timetable.service.passenger;

import com.example.timetable.entity.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> getPassengersByTrain(String trainNumber);
}
