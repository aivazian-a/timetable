package com.example.timetable.repository;

import com.example.timetable.entity.TrainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<TrainEntity, Long> {
}
