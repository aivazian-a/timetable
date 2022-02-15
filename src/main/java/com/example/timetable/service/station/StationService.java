package com.example.timetable.service.station;

import com.example.timetable.entity.StationEntity;

import java.util.List;

public interface StationService {
    StationEntity saveStation(StationEntity station);

    List<StationEntity> getStations();
}
