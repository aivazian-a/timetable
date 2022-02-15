package com.example.timetable.service.passenger;

import com.example.timetable.entity.PassengerEntity;
import com.example.timetable.entity.TicketEntity;
import com.example.timetable.repository.PassengerRepository;
import com.example.timetable.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerServiceImpl implements PassengerService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(PassengerServiceImpl.class);

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;

    @Override
    public PassengerEntity savePassenger(PassengerEntity passenger) {
        log.info("Passenger to save: " + passenger.toString());
        return passengerRepository.save(passenger);
    }

    @Override
    public List<PassengerEntity> getPassengersByTrainId(Long trainId) {
        return ticketRepository.findAllByTrainId(trainId).stream()
                .map(TicketEntity::getPassenger)
                .collect(Collectors.toList());
    }
}
