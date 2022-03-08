package com.example.timetable.error;

import com.example.timetable.util.LocalizedMessage;

public class CommonExceptionMessage {
    public static final String BUSINESS_EXCEPTION_BUNDLE = "business-exception-message";

    public static final LocalizedMessage OBJECT_NOT_FOUND = LocalizedMessage.builder()
            .messageCode("object.not.found")
            .bundle(BUSINESS_EXCEPTION_BUNDLE).build();

    public static final LocalizedMessage TRIP_NOT_FOUND = LocalizedMessage.builder()
            .messageCode("trip.not.found")
            .bundle(BUSINESS_EXCEPTION_BUNDLE).build();

    public static final LocalizedMessage PASSENGER_ALREADY_REGISTERED = LocalizedMessage.builder()
            .messageCode("passenger.already.registered")
            .bundle(BUSINESS_EXCEPTION_BUNDLE).build();

    public static final LocalizedMessage ALL_SEATS_BOUGHT = LocalizedMessage.builder()
            .messageCode("all.seats.bought")
            .bundle(BUSINESS_EXCEPTION_BUNDLE).build();
}
