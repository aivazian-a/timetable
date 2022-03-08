package com.example.timetable.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ticket")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="passenger_id", nullable=false)
    private PassengerEntity passenger;

    @ManyToOne
    @JoinColumn(name="train_id", nullable=false)
    private TrainEntity train;

    @NotNull
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
}
