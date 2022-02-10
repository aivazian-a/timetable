package com.example.timetable.controller;

import com.example.timetable.dto.TimeTableDto;
import com.example.timetable.entity.TimeTableRelation;
import com.example.timetable.entity.Train;
import com.example.timetable.service.timetable.TimeTableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class TimeTableController {
    private final TimeTableService timeTableService;

    @GetMapping("/{station}")
    public List<TimeTableRelation> getTimeTableByStation(@PathVariable String station) {
        return timeTableService.getTimeTableByStationName(station);
    }

    @GetMapping()
    public List<Train> findTrains(
            @RequestParam String fromStation,
            @RequestParam String toStation,
            @RequestParam LocalDateTime fromDate,
            @RequestParam LocalDateTime toDate
    ) {
        return timeTableService.findTrains(fromStation, toStation, fromDate, toDate);
    }

    @PostMapping
    public TimeTableRelation addTimeTable(@RequestBody TimeTableDto timeTableDto) {

        return timeTableService.addTimeTable(timeTableDto.toTimeTableRelation());
    }
}
