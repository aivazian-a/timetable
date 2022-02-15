package com.example.timetable.service.ticket;

import com.example.timetable.dto.BuyRequestDto;
import com.example.timetable.entity.TicketEntity;

public interface TicketService {
    TicketEntity buyTicket(BuyRequestDto buyRequestDto);
}
