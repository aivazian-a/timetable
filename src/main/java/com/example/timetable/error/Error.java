package com.example.timetable.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Error {
    private String fieldName;
    private String message;
}
