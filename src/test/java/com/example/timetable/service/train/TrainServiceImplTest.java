package com.example.timetable.service.train;

import com.example.timetable.entity.StationEntity;
import com.example.timetable.entity.TimetableRelationEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.repository.TimeTableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrainServiceImplTest {
    @Mock
    private TimeTableRepository timeTableRepository;

    @InjectMocks
    private TrainServiceImpl trainService;

    @Test
    void findTrainsByTimeAndStation() {
        var fromStationId = 1l;
        var toStationId = 2l;
        var fromDate = LocalDateTime.now().minusHours(2);
        var toDate = LocalDateTime.now().plusHours(1);

        var fromStation = StationEntity.builder().id(fromStationId).name("from").build();
        var toStation = StationEntity.builder().id(toStationId).name("to").build();

        var train1 = TrainEntity.builder().id(1l).number("number1").seatAmount(10).build();
        var train2 = TrainEntity.builder().id(2l).number("number2").seatAmount(10).build();
        var train3 = TrainEntity.builder().id(3l).number("number3").seatAmount(10).build();

        var timetableRelationCorrectTimeFromStationTrain1 = TimetableRelationEntity.builder()
                .id(1l)
                .station(fromStation)
                .train(train1)
                .arrivalTime(fromDate.plusMinutes(10))
                .departureTime(fromDate.plusMinutes(20))
                .build();

        var timetableRelationCorrectTimeToStationTrain1 = TimetableRelationEntity.builder()
                .id(1l)
                .station(toStation)
                .train(train1)
                .arrivalTime(toDate.plusMinutes(10))
                .departureTime(toDate.plusMinutes(20))
                .build();

        var timetableRelationBeforeFromStationTrain2 = TimetableRelationEntity.builder()
                .id(1l)
                .station(fromStation)
                .train(train1)
                .arrivalTime(fromDate.minusMinutes(10))
                .departureTime(fromDate.minusMinutes(5))
                .build();

        var timetableRelationAfterFromStationTrain2 = TimetableRelationEntity.builder()
                .id(1l)
                .station(fromStation)
                .train(train1)
                .arrivalTime(toDate.plusMinutes(10))
                .departureTime(toDate.plusMinutes(15))
                .build();

        var timetableRelationAfterPeriodToStationTrain2 = TimetableRelationEntity.builder()
                .id(1l)
                .station(toStation)
                .train(train2)
                .arrivalTime(toDate.plusMinutes(10))
                .departureTime(toDate.plusMinutes(20))
                .build();

        var timetableRelationCorrectTimeFromStationTrain3 = TimetableRelationEntity.builder()
                .id(1l)
                .station(fromStation)
                .train(train3)
                .arrivalTime(toDate.plusMinutes(10))
                .departureTime(toDate.plusMinutes(20))
                .build();

        when(timeTableRepository.findAllByStationId(fromStationId)).thenReturn(List.of(timetableRelationCorrectTimeFromStationTrain1, timetableRelationBeforeFromStationTrain2, timetableRelationAfterFromStationTrain2, timetableRelationCorrectTimeFromStationTrain3));
        when(timeTableRepository.findAllByStationId(toStationId)).thenReturn(List.of(timetableRelationCorrectTimeToStationTrain1, timetableRelationAfterPeriodToStationTrain2));

        List<TrainEntity> trainsByTimeAndStation = trainService.findTrainsByTimeAndStation(fromStationId, toStationId, fromDate, toDate);

        assertEquals(1, trainsByTimeAndStation.size());
        assertEquals(train1, trainsByTimeAndStation.get(0));
    }
}