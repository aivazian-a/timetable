package com.example.timetable.repository;

import com.example.timetable.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByFirstnameAndLastnameAndBirthdate(String firstname, String lastname, LocalDate birthDate);
}
