package com.example.timetable.service.timetable;

import com.example.timetable.dto.TimetableDto;
import com.example.timetable.entity.StationEntity;
import com.example.timetable.entity.TimetableRelationEntity;
import com.example.timetable.entity.TrainEntity;
import com.example.timetable.error.CommonExceptionMessage;
import com.example.timetable.error.ErrorCode;
import com.example.timetable.error.exception.BusinessException;
import com.example.timetable.repository.StationRepository;
import com.example.timetable.repository.TimeTableRepository;
import com.example.timetable.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TimetableServiceImpl implements TimetableService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(TimetableServiceImpl.class);

    private final TimeTableRepository timeTableRepository;
    private final StationRepository stationRepository;
    private final TrainRepository trainRepository;

    @Override
    public List<TimetableRelationEntity> getTimeTableByStationId(Long stationId) {
        return timeTableRepository.findAllByStationId(stationId);
    }

    @Override
    public TimetableRelationEntity addTimeTable(TimetableDto timetableDto) {
        StationEntity station = stationRepository.findById(timetableDto.getStationId())
                .orElseThrow(() -> new BusinessException(CommonExceptionMessage.OBJECT_NOT_FOUND, ErrorCode.OBJECT_NOT_FOUND));

        TrainEntity train = trainRepository.findById(timetableDto.getTrainId())
                .orElseThrow(() -> new BusinessException(CommonExceptionMessage.OBJECT_NOT_FOUND, ErrorCode.OBJECT_NOT_FOUND));

        TimetableRelationEntity timetableRelation = TimetableRelationEntity.builder()
                .train(train)
                .station(station)
                .arrivalTime(timetableDto.getArrivalTime())
                .departureTime(timetableDto.getDepartureTime())
                .build();

        log.info("Timetable to save: " + timetableRelation.toString());
        return timeTableRepository.save(timetableRelation);
    }
}
