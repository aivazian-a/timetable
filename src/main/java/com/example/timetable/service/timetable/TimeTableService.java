package com.example.timetable.service.timetable;

import com.example.timetable.entity.TimeTableRelation;
import com.example.timetable.entity.Train;

import java.time.LocalDateTime;
import java.util.List;

public interface TimeTableService {
    List<TimeTableRelation> getTimeTableByStationName(String station);

    List<Train> findTrains(String fromStation, String toStation, LocalDateTime fromDate, LocalDateTime toDate);

    TimeTableRelation addTimeTable(TimeTableRelation timeTableRelation);
}
