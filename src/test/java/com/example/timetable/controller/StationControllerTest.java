package com.example.timetable.controller;

import com.example.timetable.entity.StationEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.service.station.StationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
public class StationControllerTest {
    @MockBean
    private StationService stationService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getStationsShouldReturnList() throws Exception {

        when(stationService.getStations()).thenReturn(List.of(StationEntity.builder().id(1l).name("test").build()));

        mockMvc.perform(get("/api/v1/stations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("test")));
    }

    @Test
    public void saveStationShouldReturnException() throws Exception {

        mockMvc.perform(post("/api/v1/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Validation error occured")))
                .andExpect(jsonPath("type", is("VALIDATION_ERROR")));
    }

    @Test
    public void saveStationShouldBeSuccess() throws Exception {

        when(stationService.saveStation(any())).thenReturn(StationEntity.builder().id(1l).name("test").build());

        mockMvc.perform(post("/api/v1/stations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"test\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("test")));
    }
}
