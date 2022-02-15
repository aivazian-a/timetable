package com.example.timetable.error.response;

import com.example.timetable.error.ErrorCode;

public interface ErrorResponse {
    String getMessage();

    ErrorCode getType();
}
