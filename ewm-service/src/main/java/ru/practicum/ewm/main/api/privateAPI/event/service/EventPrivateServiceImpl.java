package ru.practicum.ewm.main.api.privateAPI.event.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.errorAPI.FullLimitException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.main.data.dto.event.EventRequestStatusUpdateResult;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.dto.event.NewEventDto;
import ru.practicum.ewm.main.data.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.main.data.dto.location.NewLocationDto;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;
import ru.practicum.ewm.main.data.enums.EventState;
import ru.practicum.ewm.main.data.enums.PrivateStateAction;
import ru.practicum.ewm.main.data.enums.RequestStatus;
import ru.practicum.ewm.main.data.mapper.event.EventMapper;
import ru.practicum.ewm.main.data.mapper.location.LocationMapper;
import ru.practicum.ewm.main.data.mapper.participation.ParticipationMapper;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.location.Location;
import ru.practicum.ewm.main.persistence.model.participation.ParticipationRequest;
import ru.practicum.ewm.main.persistence.model.user.User;
import ru.practicum.ewm.main.persistence.repository.CategoryRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.persistence.repository.LocationRepository;
import ru.practicum.ewm.main.persistence.repository.ParticipationRepository;
import ru.practicum.ewm.main.persistence.repository.UserRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {
    private final EventMapper eventMapper;
    private final LocationMapper locationMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;
    private final Validation validation;
    private static final int ONE_REQUEST = 1;

    @Override
    @Transactional(readOnly = true)
    public List<EventShortDto> getEventsByUser(Long userId, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return eventRepository.findAllByInitiatorId(userId, pageable).stream()
                .map(eventMapper::toEventShortDto)
                .toList();
    }

    @Override
    public EventFullDto save(Long userId, NewEventDto newEventDto) {
        User user = validation.checkUserExist(userId, userRepository);
        Category category = validation.checkCategoryExist(newEventDto.getCategory(), categoryRepository);
        Location location = locationMapper.newLocationDtoToLocation(newEventDto.getLocation());
        locationRepository.save(location);
        Event event = eventMapper.toEvent(newEventDto, location, category, user, LocalDateTime.now(),
                EventState.PENDING, 0, 0L);
        eventRepository.save(event);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    @Transactional(readOnly = true)
    public EventFullDto getEventById(Long userId, Long eventId) {
        validation.checkUserExist(userId, userRepository);
        Event event = validation.checkEventExist(eventId, eventRepository);
        return eventMapper.toEventFullDto(event);
    }

    @Override
    public EventFullDto updateEventByUser(Long userId, Long eventId, UpdateEventUserRequest updateForEvent) {
        Event event = validation.checkEventExist(eventId, eventRepository);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2)) ||
                event.getState().equals(EventState.PUBLISHED)) {
            throw new IllegalArgumentException(Constants.INVALID_EVENT);
        }
        if (updateForEvent.getStateAction() != null) {
            if (updateForEvent.getStateAction().equals(PrivateStateAction.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            } else if (updateForEvent.getStateAction().equals(PrivateStateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            } else throw new IllegalArgumentException(Constants.INVALID_STATUS);
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
        eventMapper.updateEventByPrivate(event, updateForEvent);
        return eventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getEventRequests(Long userId, Long eventId) {
        return participationRepository.findByEventIdOrderByCreatedDesc(eventId).stream()
                .map(participationMapper::toParticipationRequestDto)
                .toList();
    }

    @Override
    public EventRequestStatusUpdateResult updateEventRequestStatus(Long userId,
                                                                   Long eventId,
                                                                   EventRequestStatusUpdateRequest request) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult()
                .setRejectedRequests(new ArrayList<>())
                .setConfirmedRequests(new ArrayList<>());
        Event event = validation.checkEventExist(eventId, eventRepository);
        List<ParticipationRequest> participationRequestRequestList = participationRepository.findAllById(request.getRequestIds());
        validation.checkPendingRequestStatus(participationRequestRequestList);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            return result;
        }
        if (request.getStatus().equals(RequestStatus.REJECTED)) {
            participationRequestRequestList = participationRequestRequestList.stream()
                    .map(participation -> participation.setRequestStatus(RequestStatus.REJECTED))
                    .toList();
            participationRepository.saveAll(participationRequestRequestList);
            List<ParticipationRequestDto> participationRequestDtoList = participationRequestRequestList.stream()
                    .map(participationMapper::toParticipationRequestDto)
                    .toList();
            result.getRejectedRequests().addAll(participationRequestDtoList);
            return result;
        } else {
            if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
                throw new FullLimitException(String.format(Constants.LIMIT_IS_OVER, eventId));
            }
            participationRequestRequestList = participationRequestRequestList.stream()
                    .map(participation -> {
                        if (!event.getParticipantLimit().equals(event.getConfirmedRequests())) {
                            participation.setRequestStatus(RequestStatus.CONFIRMED);
                            event.setConfirmedRequests(event.getConfirmedRequests() + ONE_REQUEST);
                            eventRepository.save(event);
                            result.getConfirmedRequests().add(participationMapper.toParticipationRequestDto(participation));
                            return participation;
                        } else {
                            participation.setRequestStatus(RequestStatus.REJECTED);
                            result.getRejectedRequests().add(participationMapper.toParticipationRequestDto(participation));
                            return participation;
                        }
                    }).toList();
            participationRepository.saveAll(participationRequestRequestList);
            return result;
        }
    }
}