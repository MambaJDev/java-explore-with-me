package ru.practicum.ewm.server.stats.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;
import ru.practicum.ewm.server.stats.model.EndpointHit;
import ru.practicum.ewm.server.stats.model.ShortEndpointHit;
import ru.practicum.ewm.server.stats.repository.StatRepository;

@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatMapper statMapper;
    private final StatRepository statRepository;

    @Override
    public void saveStats(StatRequestDto statRequestDto) {
        EndpointHit endpointHitData = statMapper.statRequestDtoToEndpointHitData(statRequestDto);
        statRepository.save(endpointHitData);
    }

    @Override
    public List<StatResponseDto> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, Boolean unique) {
        List<ShortEndpointHit> shortEndpointHitList;
        if (uris == null) {
            shortEndpointHitList = statRepository.getAllStats(start, end);
        } else {
            shortEndpointHitList = statRepository.getAllStatsFromIpSet(uris, start, end);
        }
        if (unique) {
            return shortEndpointHitList.stream()
                    .map(shortEndpointHit -> statMapper.shortEndpointHitToStatResponseDto(
                                    shortEndpointHit, statRepository.countByAppAndUriAndTimestampBetweenWhenIpIsUnique(
                                            shortEndpointHit.getApp(), shortEndpointHit.getUri(), start, end
                                    )
                            )
                    )
                    .sorted(Comparator.comparingInt(StatResponseDto::getHits).reversed())
                    .collect(Collectors.toList());
        } else {
            return shortEndpointHitList.stream()
                    .map(shortEndpointHit -> statMapper.shortEndpointHitToStatResponseDto(
                                    shortEndpointHit, statRepository.countByAppAndUriAndTimestampBetween(
                                            shortEndpointHit.getApp(), shortEndpointHit.getUri(), start, end
                                    )
                            )
                    )
                    .sorted(Comparator.comparingInt(StatResponseDto::getHits).reversed())
                    .collect(Collectors.toList());
        }
    }
}