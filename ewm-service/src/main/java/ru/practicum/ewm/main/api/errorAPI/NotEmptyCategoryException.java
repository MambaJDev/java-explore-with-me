package ru.practicum.ewm.main.api.errorAPI;

public class NotEmptyCategoryException extends RuntimeException {
    public NotEmptyCategoryException(String message) {
        super(message);
    }
}