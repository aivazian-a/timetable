package com.example.timetable.dto;

import com.example.timetable.entity.Station;
import lombok.Data;

@Data
public class StationDto {
    private String name;

    public Station toStation() {
        return Station.builder().name(name).build();
    }
}
