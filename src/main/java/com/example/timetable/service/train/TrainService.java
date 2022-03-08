package com.example.timetable.service.train;

import com.example.timetable.entity.TrainEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainService {

    TrainEntity saveTrain(TrainEntity train);

    List<TrainEntity> getTrains();

    List<TrainEntity> findTrainsByTimeAndStation(Long fromStation, Long toStation, LocalDateTime fromDate, LocalDateTime toDate);
}
