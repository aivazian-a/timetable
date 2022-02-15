package com.example.timetable.service.timetable;

import com.example.timetable.dto.TimetableDto;
import com.example.timetable.entity.StationEntity;
import com.example.timetable.entity.TimetableRelationEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.error.ErrorCode;
import com.example.timetable.error.exception.BusinessException;
import com.example.timetable.repository.StationRepository;
import com.example.timetable.repository.TimeTableRepository;
import com.example.timetable.repository.TrainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimetableServiceImplTest {
    @Mock
    private TimeTableRepository timeTableRepository;

    @Mock
    private StationRepository stationRepository;

    @Mock
    private TrainRepository trainRepository;

    @InjectMocks
    private TimetableServiceImpl timetableService;

    @Test
    void addTimeTableSuccess() {
        var stationId = 123l;
        var trainId = 321l;

        var timetableDto = TimetableDto.builder()
                .arrivalTime(LocalDateTime.now())
                .departureTime(LocalDateTime.now().plusMinutes(15))
                .stationId(stationId)
                .trainId(trainId)
                .build();

        var staion = StationEntity.builder().id(stationId).name("station").build();
        var train = TrainEntity.builder().id(trainId).seatAmount(123).number("number123").build();
        var timetableRelation = TimetableRelationEntity.builder()
                .id(1l)
                .station(staion)
                .train(train)
                .arrivalTime(timetableDto.getArrivalTime())
                .departureTime(timetableDto.getDepartureTime())
                .build();

        when(stationRepository.findById(timetableDto.getStationId())).thenReturn(Optional.of(staion));
        when(trainRepository.findById(timetableDto.getTrainId())).thenReturn(Optional.of(train));
        when(timeTableRepository.save(any())).thenReturn(timetableRelation);

        var newTimetable = timetableService.addTimeTable(timetableDto);

        assertEquals(timetableDto.getStationId(), newTimetable.getStation().getId());
        assertEquals(timetableDto.getTrainId(), newTimetable.getTrain().getId());
        assertEquals(timetableDto.getArrivalTime(), newTimetable.getArrivalTime());
        assertEquals(timetableDto.getDepartureTime(), newTimetable.getDepartureTime());
    }

    @Test
    void addTimeTableStationNotFound() {
        var stationId = 123l;
        var trainId = 321l;

        var timetableDto = TimetableDto.builder()
                .arrivalTime(LocalDateTime.now())
                .departureTime(LocalDateTime.now().plusMinutes(15))
                .stationId(stationId)
                .trainId(trainId)
                .build();

        BusinessException businessException = assertThrows(BusinessException.class, () -> timetableService.addTimeTable(timetableDto));
        assertEquals(ErrorCode.OBJECT_NOT_FOUND, businessException.getErrorCode());
        assertEquals("Object not found", businessException.getLocalizedMessage());
    }

    @Test
    void addTimeTableTrainNotFound() {
        var stationId = 123l;
        var trainId = 321l;

        var timetableDto = TimetableDto.builder()
                .arrivalTime(LocalDateTime.now())
                .departureTime(LocalDateTime.now().plusMinutes(15))
                .stationId(stationId)
                .trainId(trainId)
                .build();
        var staion = StationEntity.builder().id(stationId).name("station").build();
        when(stationRepository.findById(timetableDto.getStationId())).thenReturn(Optional.of(staion));

        BusinessException businessException = assertThrows(BusinessException.class, () -> timetableService.addTimeTable(timetableDto));

        assertEquals(ErrorCode.OBJECT_NOT_FOUND, businessException.getErrorCode());
        assertEquals("Object not found", businessException.getLocalizedMessage());
    }
}