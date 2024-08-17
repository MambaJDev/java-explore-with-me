package ru.practicum.ewm.server.stats.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;

public interface StatService {

    ResponseEntity<Object> saveStats(StatRequestDto statRequestDto);

    List<StatResponseDto> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique);
}