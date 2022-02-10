package com.example.timetable.service.train;

import com.example.timetable.entity.Train;
import com.example.timetable.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepository;

    @Override
    public void saveTrain(Train newTrain) {
        Optional<Train> train = trainRepository.findByNumber(newTrain.getNumber());
        if (train.isPresent())
            throw new RuntimeException();

        trainRepository.save(newTrain);
    }

    @Override
    public List<Train> getTrains() {
        return trainRepository.findAll();
    }

    @Override
    public Train findTrainByNumber(String number) {
        return trainRepository.findByNumber(number).orElseThrow(() -> new RuntimeException());
    }
}
