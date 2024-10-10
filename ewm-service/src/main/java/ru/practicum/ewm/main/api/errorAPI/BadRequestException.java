package ru.practicum.ewm.main.api.errorAPI;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}