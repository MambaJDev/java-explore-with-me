package ru.practicum.ewm.main.api.publicAPI.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.publicAPI.event.service.EventPublicService;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.enums.Sort;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/events")
public class PublicEventController {
    private final EventPublicService eventPublicService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getPublicEvents(@RequestParam(required = false) String text,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                               @RequestParam(required = false) LocalDateTime rangeStart,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                               @RequestParam(required = false) LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam(required = false) Sort sort,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "10") @Positive Integer size,
                                               @NonNull HttpServletRequest request) {
        log.info("public GET-request, getEvents: text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, " +
                        "onlyAvailable={}, sort={}, from={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        return eventPublicService.getPublicEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getPublicEventById(@Positive @NotNull
                                           @PathVariable(name = "id") Long eventId,
                                           @NonNull HttpServletRequest request) {
        log.info("public GET-request, getEvent: id={}", eventId);
        log.info("client ip: {}, endpoint path: {}", request.getRemoteAddr(), request.getRequestURI());
        return eventPublicService.getPublicEventById(eventId, request);
    }
}