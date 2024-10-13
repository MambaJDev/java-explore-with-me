package ru.practicum.ewm.main.api.errorAPI;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}