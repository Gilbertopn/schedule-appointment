package com.gilbertojr.exception;

public class AppointmentTimeNotAvailableException extends RuntimeException {
    public AppointmentTimeNotAvailableException(String message) {
        super(message);
    }
}
