package com.example.timetable.error.response;

import com.example.timetable.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class BusinessErrorResponse implements ErrorResponse {
    private String error;
    private ErrorCode type;

    @Override
    public String getMessage() {
        return error;
    }

    @Override
    public ErrorCode getType() {
        return type;
    }
}
