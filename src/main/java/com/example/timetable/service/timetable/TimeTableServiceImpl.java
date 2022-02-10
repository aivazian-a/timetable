package com.example.timetable.service.timetable;

import com.example.timetable.entity.Station;
import com.example.timetable.entity.TimeTableRelation;
import com.example.timetable.entity.Train;
import com.example.timetable.repository.TimeTableRepository;
import com.example.timetable.service.station.StationService;
import com.example.timetable.service.train.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TimeTableServiceImpl implements TimeTableService {

    private final TimeTableRepository timeTableRepository;
    private final TrainService trainService;
    private final StationService stationService;

    @Override
    public List<TimeTableRelation> getTimeTableByStationName(String station) {
        return timeTableRepository.findByStationName(station);
    }

    @Override
    public List<Train> findTrains(String fromStation, String toStation, LocalDateTime fromDate, LocalDateTime toDate) {
        Station destination = stationService.getStationByName(toStation);

        return timeTableRepository.findAllByDepartureTimeBetweenAndStationName(fromDate, toDate, fromStation)
                .stream()
                .filter(it -> it.getDepartureTime().isBefore(toDate) && it.getDepartureTime().isAfter(fromDate))
                .map(it -> it.getTrain())
                .filter(train -> train.getStations().contains(destination))
                .collect(Collectors.toList());
    }

    @Override
    public TimeTableRelation addTimeTable(TimeTableRelation timeTableRelation) {
        Station station = stationService.getStationByName(timeTableRelation.getStation().getName());
        Train train = trainService.findTrainByNumber(timeTableRelation.getTrain().getNumber());

        timeTableRelation.setStation(station);
        timeTableRelation.setTrain(train);

        return timeTableRepository.save(timeTableRelation);
    }
}
