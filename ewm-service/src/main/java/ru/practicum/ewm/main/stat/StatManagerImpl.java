package ru.practicum.ewm.main.stat;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.client.stats.StatsClient;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.persistence.model.event.Event;

@Component
@RequiredArgsConstructor
public class StatManagerImpl implements StatManager {
    private final StatsClient statsClient;


    @Override
    public void sendHitEventData(HttpServletRequest request) {
        StatRequestDto statRequestDto = new StatRequestDto()
                .setApp(Constants.MAIN_SERVICE_NAME)
                .setIp(request.getRemoteAddr())
                .setUri(request.getRequestURI())
                .setTimestamp(LocalDateTime.now());
        statsClient.sendHitEventData(statRequestDto);
    }

    @Override
    public long getUniqueEventViews(HttpServletRequest request, Event event) {
        LocalDateTime start = event.getPublishedOn().withNano(0);
        LocalDateTime end = LocalDateTime.now().withNano(0);
        String[] uri = new String[]{request.getRequestURI()};
        List<StatResponseDto> statResponseDtoList = statsClient.getStats(start, end, uri, true);
        return statResponseDtoList.stream()
                .filter(statResponseDto -> statResponseDto.getApp().equals(Constants.MAIN_SERVICE_NAME)
                        && statResponseDto.getUri().equals(request.getRequestURI()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Constants.NO_DATA))
                .getHits();
    }
}