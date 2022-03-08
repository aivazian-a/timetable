package com.example.timetable.controller;

import com.example.timetable.entity.StationEntity;
import com.example.timetable.entity.TimetableRelationEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.service.timetable.TimetableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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
public class TimetableControllerTest {

    @MockBean
    private TimetableService timetableService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTimetableByStationIdShouldReturnList() throws Exception {
        Long stationId = 123l;
        TimetableRelationEntity timetableRelation = TimetableRelationEntity.builder()
                .id(1l)
                .arrivalTime(LocalDateTime.now())
                .departureTime(LocalDateTime.now().plusMinutes(10))
                .station(StationEntity.builder().id(stationId).build())
                .train(TrainEntity.builder().id(854l).build())
                .build();

        when(timetableService.getTimeTableByStationId(stationId)).thenReturn(List.of(timetableRelation));

        mockMvc.perform(get("/api/v1/timetable?stationId=" + stationId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].station.id", is(123)))
                .andExpect(jsonPath("$[0].train.id", is(854)));
    }

    @Test
    public void saveTimetableShouldReturnException() throws Exception {

        mockMvc.perform(post("/api/v1/timetable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Validation error occured")))
                .andExpect(jsonPath("type", is("VALIDATION_ERROR")));
    }

    @Test
    public void saveTimetableShouldBeSuccess() throws Exception {
        Long stationId = 123l;
        TimetableRelationEntity timetableRelation = TimetableRelationEntity.builder()
                .id(1l)
                .arrivalTime(LocalDateTime.now())
                .departureTime(LocalDateTime.now().plusMinutes(10))
                .station(StationEntity.builder().id(stationId).build())
                .train(TrainEntity.builder().id(854l).build())
                .build();
        when(timetableService.addTimeTable(any())).thenReturn(timetableRelation);

        mockMvc.perform(post("/api/v1/timetable")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"arrivalTime\": \"2022-02-14T10:53:29.817Z\", \"departureTime\": \"2022-02-14T10:53:29.817Z\", \"stationId\": 0, \"trainId\": 0}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("station.id", is(123)))
                .andExpect(jsonPath("train.id", is(854)));
    }
}
