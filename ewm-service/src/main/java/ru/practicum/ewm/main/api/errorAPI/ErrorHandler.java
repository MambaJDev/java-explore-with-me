package ru.practicum.ewm.main.api.errorAPI;


import jakarta.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(FullLimitException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError fullLimitHandle(final FullLimitException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.CONFLICT)
                .setReason("For the requested operation the conditions are not met.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationHandle(final MethodArgumentNotValidException exception) {
        log.error("400 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("Incorrectly made request.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError conflictHandle(final IllegalArgumentException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.CONFLICT)
                .setReason("Integrity constraint has been violated.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError NotFoundHandle(final NotFoundException exception) {
        log.error("404 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.NOT_FOUND)
                .setReason("The required object was not found.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError BadRequestHandle(final BadRequestException exception) {
        log.error("400 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("Incorrectly made request.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError MissingServletRequestParameterHandle(final MissingServletRequestParameterException exception) {
        log.error("400 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("Incorrectly made request.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError constraintViolationHandle(final ConstraintViolationException exception) {
        log.error("400 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("Incorrectly made request.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(WrongEnumException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validationHandle(final WrongEnumException exception) {
        log.error("400 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.BAD_REQUEST)
                .setReason("Incorrectly made request.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError psqlValidationHandle(final SQLException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.CONFLICT)
                .setReason("Integrity constraint has been violated.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError DataIntegrityViolationHandle(final DataIntegrityViolationException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.CONFLICT)
                .setReason("Integrity constraint has been violated.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(NotEmptyCategoryException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError notEmptyCategoryHandle(final NotEmptyCategoryException exception) {
        log.error("409 {}", exception.getMessage(), exception);
        return new ApiError()
                .setStatus(HttpStatus.CONFLICT)
                .setReason("For the requested operation the conditions are not met.")
                .setMessage(exception.getMessage())
                .setTimestamp(LocalDateTime.now());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError MainExceptionHandle(Throwable throwable) {
        log.error("500 {}", throwable.getMessage(), throwable);
        return new ApiError()
                .setStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .setReason("Oops, something went wrong...")
                .setMessage(throwable.getMessage())
                .setTimestamp(LocalDateTime.now());
    }
}