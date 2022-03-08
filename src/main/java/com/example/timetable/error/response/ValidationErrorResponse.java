package com.example.timetable.error.response;

import com.example.timetable.error.Error;
import com.example.timetable.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class ValidationErrorResponse implements ErrorResponse {
    private List<Error> errors;

    @Override
    public String getMessage() {
        return "Validation error occured";
    }

    @Override
    public ErrorCode getType() {
        return ErrorCode.VALIDATION_ERROR;
    }
}
