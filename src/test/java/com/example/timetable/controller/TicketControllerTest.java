package com.example.timetable.controller;

import com.example.timetable.dto.BuyRequestDto;
import com.example.timetable.entity.PassengerEntity;
import com.example.timetable.entity.StationEntity;
import com.example.timetable.entity.TicketEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.service.ticket.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {
    @MockBean
    private TicketService ticketService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void buyTicketShouldBeSuccess() throws Exception {
        TicketEntity ticket = TicketEntity.builder()
                .id(1l)
                .departureTime(LocalDateTime.now())
                .train(TrainEntity.builder().id(2l).number("testNumber").build())
                .passenger(PassengerEntity.builder().id(3l).firstname("frstname").lastname("lastname").build())
                .build();
        when(ticketService.buyTicket(any())).thenReturn(ticket);

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"passengerId\": 0,\"stationId\": 0,\"time\": \"2022-02-14T06:31:49.786Z\",\"trainId\": 0}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
//                .andExpect(jsonPath("departureTime", is(ticket.getDepartureTime().toString())))
                .andExpect(jsonPath("train.id", is(2)))
                .andExpect(jsonPath("train.number", is(ticket.getTrain().getNumber())))
                .andExpect(jsonPath("passenger.id", is(3)))
                .andExpect(jsonPath("passenger.firstname", is(ticket.getPassenger().getFirstname())))
                .andExpect(jsonPath("passenger.lastname", is(ticket.getPassenger().getLastname())));
    }

    @Test
    public void buyTicketShouldBeException() throws Exception {

        mockMvc.perform(post("/api/v1/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Validation error occured")))
                .andExpect(jsonPath("type", is("VALIDATION_ERROR")));
    }
}
