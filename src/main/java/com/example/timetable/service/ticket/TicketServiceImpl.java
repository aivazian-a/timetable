package com.example.timetable.service.ticket;

import com.example.timetable.entity.Passenger;
import com.example.timetable.entity.Ticket;
import com.example.timetable.entity.TimeTableRelation;
import com.example.timetable.entity.Train;
import com.example.timetable.repository.PassengerRepository;
import com.example.timetable.repository.TicketRepository;
import com.example.timetable.repository.TimeTableRepository;
import com.example.timetable.service.train.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TrainService trainService;
    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final TimeTableRepository timeTableRepository;

    @Override
    public Ticket buyTicket(Ticket ticket, String stationName) {
        Train train = trainService.findTrainByNumber(ticket.getTrain().getNumber());
        List<Ticket> boughtTickets = ticketRepository.findAllByTrainNumber(train.getNumber());

        int seatAmount = train.getSeatAmount();
        int boughtSeats = boughtTickets.size();
        if (boughtSeats >= seatAmount)
            throw new RuntimeException();

        Passenger passenger = ticket.getPassenger();

        boughtTickets.stream()
                .map(t -> t.getPassenger())
                .filter(p -> p.getFirstname().equalsIgnoreCase(passenger.getFirstname())
                        && p.getLastname().equalsIgnoreCase(passenger.getLastname())
                        && p.getBirthdate().isEqual(passenger.getBirthdate()))
                .findAny()
                .ifPresent(it -> {
                    throw new RuntimeException();
                });

        LocalDateTime nowPlus10min = LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES));
        List<TimeTableRelation> timeTable = timeTableRepository.findAllByStationNameAndTrainNumber(stationName, train.getNumber());

        TimeTableRelation timeTableRelation = timeTable.stream()
                .filter(it -> it.getDepartureTime().isBefore(nowPlus10min))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());

        Passenger passengerToSave = passengerRepository
                .findByFirstnameAndLastnameAndBirthdate(passenger.getFirstname(), passenger.getLastname(), passenger.getBirthdate())
                .orElse(passenger);

        Ticket ticketToSave = Ticket.builder()
                .train(train)
                .passenger(passengerToSave)
                .departureTime(timeTableRelation.getDepartureTime())
                .build();

        ticketRepository.save(ticketToSave);

        return ticket;
    }
}
