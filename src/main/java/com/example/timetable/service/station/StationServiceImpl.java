package com.example.timetable.service.station;

import com.example.timetable.entity.StationEntity;
import com.example.timetable.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StationServiceImpl implements StationService {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(StationServiceImpl.class);

    private final StationRepository stationRepository;

    @Override
    public StationEntity saveStation(StationEntity station) {
        log.info("Station to save: " + station.toString());
        return stationRepository.save(station);
    }

    @Override
    public List<StationEntity> getStations() {
        return stationRepository.findAll();
    }
}
