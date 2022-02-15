package com.example.timetable.controller;

import com.example.timetable.entity.PassengerEntity;
import com.example.timetable.service.passenger.PassengerService;
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
public class PassengerControllerTest {
    @MockBean
    private PassengerService passengerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getPassengersByTrainIdShouldReturnListOfPassengers() throws Exception {
        Long trainId = 123l;
        PassengerEntity passenger = PassengerEntity.builder().id(1l).firstname("test").lastname("testov").birthdate(LocalDate.now()).build();

        when(passengerService.getPassengersByTrainId(trainId)).thenReturn(List.of(passenger));

        mockMvc.perform(get("/api/v1/passengers?trainId=" + trainId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstname", is(passenger.getFirstname())))
                .andExpect(jsonPath("$[0].lastname", is(passenger.getLastname())))
                .andExpect(jsonPath("$[0].birthdate", is(passenger.getBirthdate().toString())));
    }

    @Test
    public void createPassengerShouldReturnException() throws Exception {
        mockMvc.perform(post("/api/v1/passengers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message", is("Validation error occured")))
                .andExpect(jsonPath("type", is("VALIDATION_ERROR")));
    }

    @Test
    public void createPassengerShouldBeSuccess() throws Exception {
        PassengerEntity passenger = PassengerEntity.builder().id(1l).firstname("test").lastname("testov").birthdate(LocalDate.now()).build();

        when(passengerService.savePassenger(any())).thenReturn(passenger);

        mockMvc.perform(post("/api/v1/passengers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"birthdate\": \"2022-02-14\", \"firstname\": \"test\", \"lastname\": \"testov\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstname", is(passenger.getFirstname())))
                .andExpect(jsonPath("lastname", is(passenger.getLastname())))
                .andExpect(jsonPath("birthdate", is(passenger.getBirthdate().toString())));
    }
}
