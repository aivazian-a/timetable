package com.example.timetable.service.train;

import com.example.timetable.entity.TimetableRelationEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.repository.TimeTableRepository;
import com.example.timetable.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    private final TrainRepository trainRepository;
    private final TimeTableRepository timeTableRepository;

    @Override
    public TrainEntity saveTrain(TrainEntity train) {
        log.info("Train to save: " + train.toString());
        return trainRepository.save(train);
    }

    @Override
    public List<TrainEntity> getTrains() {
        return trainRepository.findAll();
    }

    @Override
    public List<TrainEntity> findTrainsByTimeAndStation(Long fromStation, Long toStation, LocalDateTime fromDate, LocalDateTime toDate) {
        List<TrainEntity> trainsFromStation = timeTableRepository.findAllByStationId(fromStation)
                .stream()
                .filter(it -> it.getDepartureTime().isBefore(toDate) && it.getDepartureTime().isAfter(fromDate))
                .map(TimetableRelationEntity::getTrain)
                .distinct()
                .collect(Collectors.toList());

        List<TrainEntity> trainsToStation = timeTableRepository.findAllByStationId(toStation)
                .stream()
                .filter(it -> it.getArrivalTime().isAfter(fromDate))
                .map(TimetableRelationEntity::getTrain)
                .distinct()
                .collect(Collectors.toList());

        trainsFromStation.retainAll(trainsToStation);

        return trainsFromStation;
    }
}
