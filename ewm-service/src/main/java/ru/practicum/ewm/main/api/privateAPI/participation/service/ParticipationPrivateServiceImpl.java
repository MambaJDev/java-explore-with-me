package ru.practicum.ewm.main.api.privateAPI.participation.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.errorAPI.BadRequestException;
import ru.practicum.ewm.main.api.errorAPI.FullLimitException;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;
import ru.practicum.ewm.main.data.enums.EventState;
import ru.practicum.ewm.main.data.enums.RequestStatus;
import ru.practicum.ewm.main.data.mapper.participation.ParticipationMapper;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.participation.ParticipationRequest;
import ru.practicum.ewm.main.persistence.model.user.User;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.persistence.repository.ParticipationRepository;
import ru.practicum.ewm.main.persistence.repository.UserRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class ParticipationPrivateServiceImpl implements ParticipationPrivateService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ParticipationRepository participationRepository;
    private final ParticipationMapper participationMapper;
    private final Validation validation;
    private static final int ONE_REQUEST = 1;

    @Override
    @Transactional(readOnly = true)
    public List<ParticipationRequestDto> getParticipationRequests(Long userId) {
        validation.checkUserExist(userId, userRepository);
        return participationRepository.findByRequesterIdOrderByCreatedDesc(userId).stream()
                .map(participationMapper::toParticipationRequestDto)
                .toList();
    }

    @Override
    public ParticipationRequestDto sendParticipationRequest(Long userId, Long eventId) {
        User user = validation.checkUserExist(userId, userRepository);
        Event event = validation.checkEventExist(eventId, eventRepository);
        ParticipationRequest participationRequest = participationRepository.findByRequesterIdAndEventId(userId, eventId);

        participationRequestExceptionCheck(participationRequest, user, event);

        ParticipationRequest newParticipation = new ParticipationRequest()
                .setRequester(user)
                .setEvent(event)
                .setCreated(LocalDateTime.now())
                .setRequestStatus(RequestStatus.PENDING);
        if (event.getParticipantLimit() == 0) {
            newParticipation.setRequestStatus(RequestStatus.CONFIRMED);
        }
        if (!event.getRequestModeration() && event.getParticipantLimit() != 0) {
            newParticipation.setRequestStatus(RequestStatus.CONFIRMED);
            event.setConfirmedRequests(event.getConfirmedRequests() + ONE_REQUEST);
            eventRepository.save(event);
        }
        participationRepository.save(newParticipation);
        return participationMapper.toParticipationRequestDto(newParticipation);
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        ParticipationRequest participationRequest = checkParticipationExist(requestId, participationRepository);
        Long eventId = participationRequest.getEvent().getId();
        if (participationRequest.getRequestStatus().equals(RequestStatus.PENDING)) {
            validation.checkEventExist(eventId, eventRepository);
            participationRequest.setRequestStatus(RequestStatus.CANCELED);
            participationRepository.save(participationRequest);
        } else {
            throw new BadRequestException(Constants.NOT_PENDING);
        }
        return participationMapper.toParticipationRequestDto(participationRequest);
    }

    private ParticipationRequest checkParticipationExist(Long partId, ParticipationRepository participationRepository) {
        return participationRepository.findById(partId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.PARTICIPATION_NOT_FOUND, partId)));
    }

    private void participationRequestExceptionCheck(ParticipationRequest participationRequest, User user, Event event) {
        if (participationRequest != null) {
            throw new IllegalArgumentException(Constants.DUPLICATE_REQUEST);
        }
        if (event.getInitiator().equals(user)) {
            throw new IllegalArgumentException(Constants.WRONG_REQUESTER);
        }
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new IllegalArgumentException(Constants.NOT_PUBLISHED_EVENT);
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new FullLimitException(String.format(Constants.LIMIT_IS_OVER, event.getId()));
        }
    }
}