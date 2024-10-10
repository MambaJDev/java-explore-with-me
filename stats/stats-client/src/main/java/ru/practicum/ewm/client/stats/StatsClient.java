package ru.practicum.ewm.client.stats;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;

@Service
public class StatsClient {
    private final RestTemplate restTemplate;

    public StatsClient(@Value("${stats-server.url}") String statsServerUrl, RestTemplateBuilder builder) {
        restTemplate = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(statsServerUrl))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    public void sendHitEventData(StatRequestDto statRequestDto) {
        try {
            HttpEntity<StatRequestDto> requestEntity = new HttpEntity<>(statRequestDto, defaultHeaders());
            restTemplate.postForEntity("/hit", requestEntity, HttpStatus.class);
        } catch (RestClientException exception) {
            throw new IllegalArgumentException("BAD CONNECTION!");
        }
    }

    public List<StatResponseDto> getStats(LocalDateTime start,
                                          LocalDateTime end,
                                          String[] uris,
                                          Boolean unique) {
        if (uris == null) {
            uris = new String[]{"0"};
        }
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );
        ResponseEntity<StatResponseDto[]> responseListDto;
        try {
            responseListDto = restTemplate.getForEntity(
                    "/stats?start={start}&end={end}&uris={uris}&unique={unique}",
                    StatResponseDto[].class,
                    parameters);
        } catch (RestClientException exception) {
            throw new IllegalArgumentException("BAD CONNECTION!");
        }
        return Arrays.asList(Objects.requireNonNull(responseListDto.getBody()));
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return headers;
    }
}