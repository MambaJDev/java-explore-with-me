package ru.practicum.ewm.main.api.errorAPI;

public class FullLimitException extends RuntimeException {
    public FullLimitException(String message) {
        super(message);
    }
}