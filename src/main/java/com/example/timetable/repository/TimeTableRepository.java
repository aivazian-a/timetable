package com.example.timetable.repository;

import com.example.timetable.entity.TimeTableRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeTableRepository extends JpaRepository<TimeTableRelation, Long> {

    List<TimeTableRelation> findByStationName(String stationName);

    List<TimeTableRelation> findAllByStationNameAndTrainNumber(String stationName, String trainNumber);

    List<TimeTableRelation> findAllByDepartureTimeBetweenAndStationName(LocalDateTime from, LocalDateTime to, String stationName);
}
