package com.example.timetable.service.ticket;

import com.example.timetable.entity.Ticket;

public interface TicketService {
    Ticket buyTicket(Ticket ticket, String stationName);
}
