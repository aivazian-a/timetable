package com.example.timetable.service.station;

import com.example.timetable.entity.Station;
import com.example.timetable.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    public void saveStation(Station newStation) {
        Optional<Station> station = stationRepository.findByName(newStation.getName());
        if (station.isPresent()) {
            throw new RuntimeException();
        }
        stationRepository.save(newStation);
    }

    @Override
    public List<Station> getStations() {
        return stationRepository.findAll();
    }

    @Override
    public Station getStationByName(String name) {
        return stationRepository.findByName(name).orElseThrow(() -> new RuntimeException());
    }
}
