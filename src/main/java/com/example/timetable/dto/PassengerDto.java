package com.example.timetable.dto;

import com.example.timetable.entity.Passenger;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerDto {
    private String firstname;
    private String lastname;
    private LocalDate birthdate;

    public Passenger toPassenger() {
        return Passenger.builder()
                .firstname(firstname)
                .lastname(lastname)
                .birthdate(birthdate)
                .build();
    }
}
