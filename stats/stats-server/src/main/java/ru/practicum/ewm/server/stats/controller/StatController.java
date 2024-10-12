package ru.practicum.ewm.server.stats.controller;


import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.constant.Constants;
import ru.practicum.ewm.dto.stats.StatRequestDto;
import ru.practicum.ewm.dto.stats.StatResponseDto;
import ru.practicum.ewm.server.stats.error.BadRequestException;
import ru.practicum.ewm.server.stats.service.StatService;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatController {
    private final StatService statService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public void saveStats(@RequestBody @Valid StatRequestDto statRequestDto) {
        if (statRequestDto.getUri() == null) {
            statRequestDto.setUri(Constants.ZERO);
        }
        log.info("post запрос на сохранение статистики");
        statService.saveStats(statRequestDto);
    }

    @GetMapping("/stats")
    public List<StatResponseDto> getStats(@RequestParam @PastOrPresent @NotNull
                                          @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime start,
                                          @RequestParam @PastOrPresent @NotNull
                                          @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime end,
                                          @RequestParam(required = false, defaultValue = Constants.ZERO) String[] uris,
                                          @RequestParam(required = false, defaultValue = Constants.FALSE_VALUE) Boolean unique) {
        if (end.isBefore(start)) {
            log.error("Время бронирования некорректно");
            throw new BadRequestException(Constants.WRONG_REQUEST_PERIOD);
        }
        log.info("get запрос на получение статистики");
        return statService.getStats(start, end, uris, unique);
    }
}