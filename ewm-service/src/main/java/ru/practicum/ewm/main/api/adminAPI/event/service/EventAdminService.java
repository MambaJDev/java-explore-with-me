package ru.practicum.ewm.main.api.adminAPI.event.service;

import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.UpdateEventAdminRequest;

public interface EventAdminService {

    List<EventFullDto> getEventsByAdmin(List<Long> users, List<String> states, List<Long> categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest request);
}