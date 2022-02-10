package com.example.timetable.dto;

import com.example.timetable.entity.Ticket;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketDto {
    private String station;
    private PassengerDto passenger;
    private TrainDto train;
    private LocalDateTime departureTime;

    public Ticket toTicket() {
        return Ticket.builder()
                .passenger(passenger.toPassenger())
                .train(train.toTrain())
                .departureTime(departureTime)
                .build();
    }
}
