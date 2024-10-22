package ru.practicum.ewm.server.stats.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;
import ru.practicum.ewm.server.stats.model.EndpointHit;
import ru.practicum.ewm.server.stats.model.StatDataView;
import ru.practicum.ewm.server.stats.repository.StatRepository;


@Service
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatMapper statMapper;
    private final StatRepository statRepository;

    @Override
    public void saveStats(StatRequestDto statRequestDto) {
        EndpointHit endpointHit = statMapper.statRequestDtoToEndpointHit(statRequestDto);
        statRepository.save(endpointHit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StatResponseDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        List<StatDataView> statDataViews;
        if (uris[0].equals("0")) {
            if (unique) {
                statDataViews = statRepository.getAllStatDataViewWithDistinctIp(start, end);
            } else {
                statDataViews = statRepository.getAllStatDataView(start, end);
            }
        } else {
            if (unique) {
                statDataViews = statRepository.getAllStatDataViewInUrisAndDistinctIp(uris, start, end);
            } else {
                statDataViews = statRepository.getAllStatDataViewInUris(uris, start, end);
            }
        }
        return statDataViews.stream()
                .map(statMapper::statDataViewtToStatResponseDto)
                .toList();
    }
}