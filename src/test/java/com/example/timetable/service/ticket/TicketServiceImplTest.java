package com.example.timetable.service.ticket;

import com.example.timetable.dto.BuyRequestDto;
import com.example.timetable.entity.*;
import com.example.timetable.error.ErrorCode;
import com.example.timetable.error.exception.BusinessException;
import com.example.timetable.repository.PassengerRepository;
import com.example.timetable.repository.TicketRepository;
import com.example.timetable.repository.TimeTableRepository;
import com.example.timetable.repository.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {
    private static final long PASSENGER_ID = 123;
    private static final long TRAIN_ID = 353;
    private static final long STATION_ID = 353;
    private static final LocalDateTime DEPARTURE_TIME = LocalDateTime.now().plusHours(1);
    private static final LocalDateTime ARRIVAL_TIME = LocalDateTime.now().plusMinutes(50);

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private TimeTableRepository timeTableRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    private BuyRequestDto buyRequestDto;

    @BeforeEach
    private void beforeEach() {
        buyRequestDto = createBuyRequestDto();
    }

    @Test
    void buyTicketSuccess() {
        var excistedPassenger = createPassenger(54l, "firstname", "lastname");
        var newPassenger = createPassenger(PASSENGER_ID, "adsgf", "lkjkhgh");
        var train = createTrain();
        var station = StationEntity.builder().id(STATION_ID).name("station").build();
        var ticket = createTicket(newPassenger, train, DEPARTURE_TIME);
        var timetableRelation = TimetableRelationEntity.builder()
                .train(train)
                .station(station)
                .departureTime(DEPARTURE_TIME)
                .id(97l)
                .build();


        when(passengerRepository.findById(buyRequestDto.getPassengerId())).thenReturn(Optional.of(newPassenger));
        when(trainRepository.findById(buyRequestDto.getTrainId())).thenReturn(Optional.of(train));
        when(ticketRepository.findAllByTrainId(buyRequestDto.getTrainId())).thenReturn(Collections.emptyList());
        when(ticketRepository.save(any())).thenReturn(ticket);
        when(timeTableRepository.findAllByStationId(buyRequestDto.getStationId())).thenReturn(List.of(timetableRelation));

        var boughtTicket = ticketService.buyTicket(buyRequestDto);
        assertEquals(buyRequestDto.getTrainId(), boughtTicket.getTrain().getId());
        assertEquals(buyRequestDto.getPassengerId(), boughtTicket.getPassenger().getId());
        assertEquals(buyRequestDto.getTime(), boughtTicket.getDepartureTime());
    }


    @Test
    void buyTicketPassengerNotFoundException() {
        when(passengerRepository.findById(buyRequestDto.getPassengerId())).thenReturn(Optional.empty());

        BusinessException businessException = assertThrows(BusinessException.class, () -> ticketService.buyTicket(buyRequestDto));
        assertEquals(ErrorCode.OBJECT_NOT_FOUND, businessException.getErrorCode());
        assertEquals("Object not found", businessException.getLocalizedMessage());
    }

    @Test
    void buyTicketTrainNotFound() {
        var passenger = createPassenger(PASSENGER_ID, "firstname", "lastname");

        when(passengerRepository.findById(buyRequestDto.getPassengerId())).thenReturn(Optional.of(passenger));
        when(trainRepository.findById(buyRequestDto.getTrainId())).thenReturn(Optional.empty());

        BusinessException businessException = assertThrows(BusinessException.class, () -> ticketService.buyTicket(buyRequestDto));
        assertEquals(ErrorCode.OBJECT_NOT_FOUND, businessException.getErrorCode());
        assertEquals("Object not found", businessException.getLocalizedMessage());
    }

    @Test
    void buyTicketPassengerAlreadyRegistered() {
        var passenger = createPassenger(PASSENGER_ID, "firstname", "lastname");
        var train = createTrain();
        var ticket = createTicket(passenger, train, DEPARTURE_TIME);

        when(passengerRepository.findById(buyRequestDto.getPassengerId())).thenReturn(Optional.of(passenger));
        when(trainRepository.findById(buyRequestDto.getTrainId())).thenReturn(Optional.of(train));
        when(ticketRepository.findAllByTrainId(buyRequestDto.getTrainId())).thenReturn(List.of(ticket));

        BusinessException businessException = assertThrows(BusinessException.class, () -> ticketService.buyTicket(buyRequestDto));
        assertEquals(ErrorCode.PASSENGER_ALREADY_REGISTERED, businessException.getErrorCode());
        assertEquals("Such passenger already registered", businessException.getLocalizedMessage());
    }

    @Test
    void buyTicketAllSeatsBooked() {
        var excistedPassenger = createPassenger(54l, "firstname", "lastname");
        var newPassenger = createPassenger(PASSENGER_ID, "adsgf", "lkjkhgh");
        var train = createTrain();
        var ticket = createTicket(excistedPassenger, train, DEPARTURE_TIME);

        when(passengerRepository.findById(buyRequestDto.getPassengerId())).thenReturn(Optional.of(newPassenger));
        when(trainRepository.findById(buyRequestDto.getTrainId())).thenReturn(Optional.of(train));
        when(ticketRepository.findAllByTrainId(buyRequestDto.getTrainId())).thenReturn(List.of(ticket));

        BusinessException businessException = assertThrows(BusinessException.class, () -> ticketService.buyTicket(buyRequestDto));
        assertEquals(ErrorCode.ALL_SEATS_BOUGHT, businessException.getErrorCode());
        assertEquals("All seats are bought", businessException.getLocalizedMessage());
    }

    @Test
    void buyTicketSiutableTripNotFound() {
        buyRequestDto.setTime(DEPARTURE_TIME.minusMinutes(9));
        var excistedPassenger = createPassenger(54l, "firstname", "lastname");
        var newPassenger = createPassenger(PASSENGER_ID, "adsgf", "lkjkhgh");
        var train = createTrain();
        var station = StationEntity.builder().id(STATION_ID).name("station").build();
        var ticket = createTicket(excistedPassenger, train, DEPARTURE_TIME);
        var timetableRelation = TimetableRelationEntity.builder()
                .train(train)
                .station(station)
                .departureTime(DEPARTURE_TIME)
                .id(97l)
                .build();


        when(passengerRepository.findById(buyRequestDto.getPassengerId())).thenReturn(Optional.of(newPassenger));
        when(trainRepository.findById(buyRequestDto.getTrainId())).thenReturn(Optional.of(train));
        when(ticketRepository.findAllByTrainId(buyRequestDto.getTrainId())).thenReturn(Collections.emptyList());
        when(timeTableRepository.findAllByStationId(buyRequestDto.getStationId())).thenReturn(List.of(timetableRelation));

        BusinessException businessException = assertThrows(BusinessException.class, () -> ticketService.buyTicket(buyRequestDto));
        assertEquals(ErrorCode.TRIP_NOT_FOUND, businessException.getErrorCode());
        assertEquals("Suitable trip not found", businessException.getLocalizedMessage());
    }

    private BuyRequestDto createBuyRequestDto() {
        return BuyRequestDto.builder().passengerId(PASSENGER_ID).trainId(TRAIN_ID).stationId(STATION_ID).time(DEPARTURE_TIME).build();
    }

    private PassengerEntity createPassenger(Long id, String firstname, String lastname) {
        return PassengerEntity.builder()
                .id(id)
                .lastname(lastname)
                .firstname(firstname)
                .birthdate(LocalDate.now().minusYears(20))
                .build();
    }

    private TrainEntity createTrain() {
        return TrainEntity.builder().id(TRAIN_ID).number("number").seatAmount(1).build();
    }

    private TicketEntity createTicket(PassengerEntity passenger, TrainEntity train, LocalDateTime time) {
        return TicketEntity.builder()
                .passenger(passenger)
                .train(train)
                .departureTime(time)
                .build();
    }
}