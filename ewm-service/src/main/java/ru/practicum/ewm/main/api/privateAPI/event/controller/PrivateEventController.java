package ru.practicum.ewm.main.api.privateAPI.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.privateAPI.event.service.EventPrivateService;
import ru.practicum.ewm.main.data.constants.DefaultValue;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.data.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.dto.event.NewEventDto;
import ru.practicum.ewm.main.data.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventPrivateService eventPrivateService;

    @GetMapping
    public List<EventShortDto> getEventsByUser(@PathVariable Long userId,
                                               @PositiveOrZero
                                               @RequestParam(defaultValue = DefaultValue.ZERO) Integer from,
                                               @Positive
                                               @RequestParam(defaultValue = DefaultValue.SIZE_10) Integer size) {
        return eventPrivateService.getEventsByUser(userId, from, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEventByUser(@PathVariable @NotNull Long userId,
                                       @RequestBody @Valid NewEventDto newEventDto) {
        return eventPrivateService.save(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventByIdByUser(@PathVariable @NotNull Long userId,
                                           @PathVariable @NotNull Long eventId) {
        return eventPrivateService.getEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable @NotNull Long userId,
                                          @PathVariable @NotNull Long eventId,
                                          @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        return eventPrivateService.updateEventByUser(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("{eventId}/requests")
    public List<ParticipationRequestDto> getEventRequestsByUser(@PathVariable @NotNull Long userId,
                                                                @PathVariable @NotNull Long eventId) {
        return eventPrivateService.getEventRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    public EventRequestStatusUpdateResult updateEventRequestStatus(@PathVariable @NotNull Long userId,
                                                                   @PathVariable @NotNull Long eventId,
                                                                   @RequestBody @Valid EventRequestStatusUpdateRequest request) {
        return eventPrivateService.updateEventRequestStatus(userId, eventId, request);
    }
}