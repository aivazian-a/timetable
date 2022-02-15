package com.example.timetable.controller;

import com.example.timetable.entity.PassengerEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.service.timetable.TimetableService;
import com.example.timetable.service.train.TrainService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainControllerTest {

    @MockBean
    private TrainService trainService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTrainsShouldReturnList() throws Exception {
        TrainEntity train = TrainEntity.builder().id(1l).number("123").seatAmount(778).build();
        when(trainService.getTrains()).thenReturn(List.of(train));

        mockMvc.perform(get("/api/v1/trains")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].number", is(train.getNumber())))
                .andExpect(jsonPath("$[0].seatAmount", is(train.getSeatAmount())));
    }

    @Test
    public void saveTrainSuccess() throws Exception {
        TrainEntity train = TrainEntity.builder().id(1l).number("123").seatAmount(778).build();

        when(trainService.saveTrain(any())).thenReturn(train);

        mockMvc.perform(post("/api/v1/trains")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"number\": \"123\", \"seatAmount\": 1}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("number", is(train.getNumber())))
                .andExpect(jsonPath("seatAmount", is(train.getSeatAmount())));
    }
}
