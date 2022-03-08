package com.example.timetable.service.ticket;

import com.example.timetable.dto.BuyRequestDto;
import com.example.timetable.entity.PassengerEntity;
import com.example.timetable.entity.TicketEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.error.CommonExceptionMessage;
import com.example.timetable.error.ErrorCode;
import com.example.timetable.error.exception.BusinessException;
import com.example.timetable.repository.PassengerRepository;
import com.example.timetable.repository.TicketRepository;
import com.example.timetable.repository.TimeTableRepository;
import com.example.timetable.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    private final PassengerRepository passengerRepository;
    private final TicketRepository ticketRepository;
    private final TrainRepository trainRepository;
    private final TimeTableRepository timeTableRepository;

    @Override
    public TicketEntity buyTicket(BuyRequestDto buyRequestDto) {
        PassengerEntity passenger = passengerRepository.findById(buyRequestDto.getPassengerId())
                .orElseThrow(() -> new BusinessException(CommonExceptionMessage.OBJECT_NOT_FOUND, ErrorCode.OBJECT_NOT_FOUND));

        TrainEntity train = trainRepository.findById(buyRequestDto.getTrainId())
                .orElseThrow(() -> new BusinessException(CommonExceptionMessage.OBJECT_NOT_FOUND, ErrorCode.OBJECT_NOT_FOUND));

        List<TicketEntity> boughtTickets = ticketRepository.findAllByTrainId(buyRequestDto.getTrainId());

        checkPassengerAlreadyRegistered(boughtTickets, passenger);

        checkTrainSeatsAmount(train, boughtTickets);

        checkDepartureTime(buyRequestDto);

        TicketEntity ticket = TicketEntity.builder()
                .passenger(passenger)
                .train(train)
                .departureTime(buyRequestDto.getTime())
                .build();

        log.info("Ticket to save: " + ticket);
        return ticketRepository.save(ticket);
    }

    private void checkPassengerAlreadyRegistered(List<TicketEntity> boughtTickets, PassengerEntity passenger) {
        boughtTickets.stream()
                .map(TicketEntity::getPassenger)
                .filter(p -> p.equals(passenger))
                .findAny()
                .ifPresent(it -> {
                    throw new BusinessException(
                            CommonExceptionMessage.PASSENGER_ALREADY_REGISTERED,
                            ErrorCode.PASSENGER_ALREADY_REGISTERED);
                });
    }

    private void checkTrainSeatsAmount(TrainEntity train, List<TicketEntity> boughtTickets) {
        if (train.getSeatAmount() <= boughtTickets.size())
            throw new BusinessException(CommonExceptionMessage.ALL_SEATS_BOUGHT, ErrorCode.ALL_SEATS_BOUGHT);
    }

    private void checkDepartureTime(BuyRequestDto buyRequestDto) {
        timeTableRepository
                .findAllByStationId(buyRequestDto.getStationId())
                .stream().filter(it -> it.getDepartureTime().equals(buyRequestDto.getTime())
                        && it.getDepartureTime().isAfter(LocalDateTime.now().plusMinutes(10)))
                .findFirst()
                .orElseThrow(() -> new BusinessException(CommonExceptionMessage.TRIP_NOT_FOUND, ErrorCode.TRIP_NOT_FOUND));
    }
}
