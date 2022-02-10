package com.example.timetable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeTableRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime arrivalTime;

    private LocalDateTime departureTime;

    @ManyToOne
    @JoinColumn(name="station_id", nullable=false)
    private Station station;

    @ManyToOne
    @JoinColumn(name="train_id", nullable=false)
    private Train train;
}
