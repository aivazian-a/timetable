package com.example.timetable.dto;

import com.example.timetable.entity.TimeTableRelation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeTableDto {
    private LocalDateTime arrival;
    private LocalDateTime departure;
    private StationDto station;
    private TrainDto train;

    public TimeTableRelation toTimeTableRelation() {
        return TimeTableRelation.builder()
                .train(train.toTrain())
                .station(station.toStation())
                .departureTime(departure)
                .arrivalTime(arrival)
                .build();
    }
}
