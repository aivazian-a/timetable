package com.example.timetable.service.timetable;

import com.example.timetable.dto.TimetableDto;
import com.example.timetable.entity.TimetableRelationEntity;

import java.util.List;

public interface TimetableService {
    List<TimetableRelationEntity> getTimeTableByStationId(Long stationId);

    TimetableRelationEntity addTimeTable(TimetableDto timetableDto);
}
