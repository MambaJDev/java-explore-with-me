package ru.practicum.ewm.main.api.publicAPI.event.service;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.enums.EventState;
import ru.practicum.ewm.main.data.enums.Sort;
import ru.practicum.ewm.main.data.mapper.event.EventMapper;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.stat.StatManager;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final StatManager statManager;
    private final Validation validation;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getPublicEvents(String text,
                                               List<Long> categories,
                                               Boolean paid,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Boolean onlyAvailable,
                                               Sort sort,
                                               Integer from,
                                               Integer size,
                                               HttpServletRequest servletRequest) {
        validation.validateDates(rangeStart, rangeEnd);
        statManager.sendHitEventData(servletRequest);
        LocalDateTime start = rangeStart != null ? rangeStart : LocalDateTime.now();
        LocalDateTime end = rangeEnd != null ? rangeEnd : LocalDateTime.now().plusYears(10);
        Pageable page = PageRequest.of(from / size, size);
        List<Event> events = eventRepository.getPublicEvents(text, categories, paid, start, end, page);
        if (events.isEmpty()) {
            return Collections.emptyList();
        }
        if (onlyAvailable) {
            events = events.stream()
                    .filter(event -> event.getParticipantLimit() > event.getConfirmedRequests())
                    .toList();
        }
        if (sort != null) {
            validation.validateEnums(sort);
            if (sort.equals(Sort.EVENT_DATE)) {
                events.sort(Comparator.comparing(Event::getEventDate));
            } else {
                events.sort(Comparator.comparing(Event::getViews));
            }
        }
        return events.stream()
                .map(eventMapper::toEventShortDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getPublicEventById(Long eventId, HttpServletRequest servletRequest) {
        Event event = validation.checkEventExist(eventId, eventRepository);
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new NotFoundException(String.format(Constants.NOT_PUBLISHED_EVENT, eventId));
        }
        statManager.sendHitEventData(servletRequest);
        Long views = statManager.getUniqueEventViews(servletRequest, event);
        event.setViews(views);
        return eventMapper.toEventFullDto(event);
    }
}