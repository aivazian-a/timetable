package com.example.timetable.service.train;

import com.example.timetable.entity.Train;

import java.util.List;

public interface TrainService {
    void saveTrain(Train train);

    List<Train> getTrains();

    Train findTrainByNumber(String number);
}
