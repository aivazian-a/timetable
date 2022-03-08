package com.example.timetable.service.passenger;

import com.example.timetable.entity.PassengerEntity;

import java.util.List;

public interface PassengerService {

    PassengerEntity savePassenger(PassengerEntity passenger);

    List<PassengerEntity> getPassengersByTrainId(Long trainId);
}
