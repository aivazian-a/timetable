package com.example.timetable.controller;

import com.example.timetable.dto.TimetableDto;
import com.example.timetable.entity.TimetableRelationEntity;
import com.example.timetable.service.timetable.TimetableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/timetable")
@RequiredArgsConstructor
public class TimetableContoller {

    private final TimetableService timetableService;

    @PostMapping
    public TimetableRelationEntity addTimeTable(@RequestBody @Valid TimetableDto timeTableRelation) {
        return timetableService.addTimeTable(timeTableRelation);
    }

    @GetMapping
    public List<TimetableRelationEntity> getTimeTableByStation(@RequestParam Long stationId) {
        return timetableService.getTimeTableByStationId(stationId);
    }
}
