package ru.practicum.ewm.main.api.errorAPI;

public class WrongEnumException extends RuntimeException {
    public WrongEnumException(String message) {
        super(message);
    }
}