package ru.practicum.ewm.main.api.privateAPI.participation.service;

import java.util.List;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;

public interface ParticipationPrivateService {
    List<ParticipationRequestDto> getParticipationRequests(Long userId);

    ParticipationRequestDto sendParticipationRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);
}