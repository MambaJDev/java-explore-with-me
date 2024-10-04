package ru.practicum.ewm.main.api.publicAPI.event.service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.enums.Sort;

public interface EventPublicService {

    List<EventShortDto> getPublicEvents(String text,
                                        List<Long> categories,
                                        Boolean paid,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        Boolean onlyAvailable,
                                        Sort sort,
                                        Integer from,
                                        Integer size,
                                        HttpServletRequest servletRequest);

    EventFullDto getPublicEventById(Long eventId, HttpServletRequest request);
}