package ru.practicum.ewm.main.api.adminAPI.event.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.UpdateEventAdminRequest;
import ru.practicum.ewm.main.data.dto.location.NewLocationDto;
import ru.practicum.ewm.main.data.enums.AdminStateAction;
import ru.practicum.ewm.main.data.enums.EventState;
import ru.practicum.ewm.main.data.mapper.event.EventMapper;
import ru.practicum.ewm.main.data.mapper.location.LocationMapper;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.location.Location;
import ru.practicum.ewm.main.persistence.repository.CategoryRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.persistence.repository.LocationRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final Validation validation;

    @Override
    @Transactional(readOnly = true)
    public List<EventFullDto> getEventsByAdmin(List<Long> users,
                                               List<String> states,
                                               List<Long> categories,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Integer from,
                                               Integer size) {
        Pageable page = PageRequest.of(from / size, size);

        return eventRepository.getAllEventsByAdmin(users, states, categories, rangeStart, rangeEnd, page).stream()
                .toList().stream()
                .map(eventMapper::toEventFullDto)
                .toList();
    }

    @Override
    public EventFullDto updateEventByAdmin(Long eventId, UpdateEventAdminRequest updateForEvent) {
        Event event = validation.checkEventExist(eventId, eventRepository);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1)) ||
                !event.getState().equals(EventState.PENDING)) {
            throw new IllegalArgumentException(Constants.INVALID_EVENT);
        }
        if (updateForEvent.getStateAction() != null) {
            if (updateForEvent.getStateAction().equals(AdminStateAction.PUBLISH_EVENT)) {
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
                event.setViews(0L);
            } else if (updateForEvent.getStateAction().equals(AdminStateAction.REJECT_EVENT)) {
                event.setState(EventState.CANCELED);
            }
        }
        if (updateForEvent.getCategory() != null) {
            Category category = validation.checkCategoryExist(updateForEvent.getCategory(), categoryRepository);
            event.setEventCategory(category);
        }
        if (updateForEvent.getLocation() != null) {
            NewLocationDto newLocationDto = updateForEvent.getLocation();
            Location location = event.getEventLocation();
            locationMapper.updateLocation(location, newLocationDto);
            locationRepository.save(location);
            event.setEventLocation(location);
        }
        eventMapper.updateEventByAdmin(event, updateForEvent);
        eventRepository.save(event);
        return eventMapper.toEventFullDto(event);
    }
}