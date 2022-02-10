package com.example.timetable.dto;

import com.example.timetable.entity.Station;
import com.example.timetable.entity.Train;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class TrainDto {
    private String number;
    private Integer seatAmount;
    private List<StationDto> stations;

    public Train toTrain() {
        List<Station> stationist = null;

        if (stations != null) {
            stationist = stations.stream()
                    .map(StationDto::toStation)
                    .collect(Collectors.toList());
        }

        return Train.builder()
                .number(number)
                .seatAmount(seatAmount)
                .stations(stationist)
                .build();
    }
}
