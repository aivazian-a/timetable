package com.example.timetable.service.station;

import com.example.timetable.entity.Station;

import java.util.List;

public interface StationService {
    void saveStation(Station station);

    List<Station> getStations();

    Station getStationByName(String name);
}
