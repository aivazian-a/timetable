package com.example.timetable.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class BuyRequestDto {
    @NotNull
    private Long trainId;

    @NotNull
    private Long stationId;

    @NotNull
    private Long passengerId;

    @NotNull
    private LocalDateTime time;
}
