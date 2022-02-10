package com.example.timetable.controller;

import com.example.timetable.dto.TicketDto;
import com.example.timetable.entity.Ticket;
import com.example.timetable.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/passengers")
@RequiredArgsConstructor
public class TicketContoller {
    private final TicketService passengerService;

    @PostMapping("/buy")
    public Ticket buyTicket(@RequestBody TicketDto ticketDto) {
        return passengerService.buyTicket(ticketDto.toTicket(), ticketDto.getStation());
    }
}
