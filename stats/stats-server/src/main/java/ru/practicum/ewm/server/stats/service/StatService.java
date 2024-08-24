package ru.practicum.ewm.server.stats.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;

public interface StatService {

    void saveStats(StatRequestDto statRequestDto);

    List<StatResponseDto> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique);
}