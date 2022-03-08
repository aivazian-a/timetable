package com.example.timetable.repository;

import com.example.timetable.entity.TimetableRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeTableRepository extends JpaRepository<TimetableRelationEntity, Long> {

    List<TimetableRelationEntity> findAllByStationId(Long stationId);
}
