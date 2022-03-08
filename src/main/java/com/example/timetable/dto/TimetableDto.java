package com.example.timetable.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class TimetableDto {
    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private Long trainId;

    @NotNull
    private Long stationId;
}
