package ru.practicum.ewm.server.stats.service;

import org.mapstruct.Mapper;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;
import ru.practicum.ewm.server.stats.model.EndpointHit;
import ru.practicum.ewm.server.stats.model.ShortEndpointHit;

@Mapper(componentModel = "spring")
public interface StatMapper {
    EndpointHit statRequestDtoToEndpointHitData(StatRequestDto statRequestDto);

    StatResponseDto shortEndpointHitToStatResponseDto(ShortEndpointHit shortEndpointHit, Integer hits);
}