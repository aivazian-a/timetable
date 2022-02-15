package com.example.timetable.error;

import com.example.timetable.error.response.BusinessErrorResponse;
import com.example.timetable.error.response.ErrorResponse;
import com.example.timetable.error.exception.BusinessException;
import com.example.timetable.error.response.ValidationErrorResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionMapper {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(ExceptionMapper.class);

    @ExceptionHandler(value = BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error(e.getMessage(), e);
        BusinessErrorResponse response = BusinessErrorResponse.builder()
                .error(e.getMessage())
                .type(e.getErrorCode()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        BusinessException businessException = new BusinessException(CommonExceptionMessage.OBJECT_NOT_FOUND, ErrorCode.OBJECT_NOT_FOUND);
        BusinessErrorResponse response = BusinessErrorResponse.builder()
                .error(businessException.getLocalizedMessage())
                .type(businessException.getErrorCode()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        log.error("Validation error", e);
        final List<Error> errors = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.add(Error.builder()
                    .fieldName(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build());
        }

        return ResponseEntity.badRequest().body(ValidationErrorResponse.builder()
                .errors(errors).build());
    }

}
