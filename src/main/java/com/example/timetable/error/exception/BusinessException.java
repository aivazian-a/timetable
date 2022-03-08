package com.example.timetable.error.exception;

import com.example.timetable.error.ErrorCode;
import com.example.timetable.util.LocalizedMessage;
import com.example.timetable.util.Translator;
import lombok.Getter;

@Getter
public class BusinessException extends AppException{
    private ErrorCode errorCode;

    public BusinessException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(LocalizedMessage localizedMessage, ErrorCode errorCode) {
        super(Translator.localize(localizedMessage));
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
