package com.example.timetable.util;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class LocalizedMessage {
    private String bundle;
    private String messageCode;
}
