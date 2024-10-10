package ru.practicum.ewm.main.api.privateAPI.event.service;

import java.util.List;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.data.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.dto.event.NewEventDto;
import ru.practicum.ewm.main.data.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;

public interface EventPrivateService {

    List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size);

    EventFullDto save(Long userId, NewEventDto newEventDto);

    EventFullDto getEventById(Long userId, Long eventId);

    EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest);

    List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateEventRequestStatus(Long userId,
                                                            Long eventId,
                                                            EventRequestStatusUpdateRequest request);
}