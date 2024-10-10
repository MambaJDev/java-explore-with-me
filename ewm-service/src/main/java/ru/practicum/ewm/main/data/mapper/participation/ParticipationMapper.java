package ru.practicum.ewm.main.data.mapper.participation;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;
import ru.practicum.ewm.main.persistence.model.participation.ParticipationRequest;

@Mapper(componentModel = "spring")
public interface ParticipationMapper {

    @Mapping(source = "participationRequest.requestStatus", target = "status")
    @Mapping(source = "participationRequest.event.id", target = "event")
    @Mapping(source = "participationRequest.requester.id", target = "requester")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest);
}