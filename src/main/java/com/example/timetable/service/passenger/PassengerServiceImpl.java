package com.example.timetable.service.passenger;

import com.example.timetable.entity.Passenger;
import com.example.timetable.service.train.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerServiceImpl implements PassengerService {
    private final TrainService trainService;

    @Override
    public List<Passenger> getPassengersByTrain(String trainNumber) {
        return trainService.findTrainByNumber(trainNumber)
                .getTickets().stream()
                .map(ticket -> ticket.getPassenger())
                .collect(Collectors.toList());
    }
}
