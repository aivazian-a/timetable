package com.example.timetable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time_table_relation")
public class TimetableRelationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @NotNull
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @ManyToOne
    @JoinColumn(name="station_id", nullable=false)
    private StationEntity station;

    @ManyToOne
    @JoinColumn(name="train_id", nullable=false)
    private TrainEntity train;
}
