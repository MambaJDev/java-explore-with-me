package ru.practicum.ewm.dto.stats;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class StatResponseDto {
    private String app;
    private String uri;
    private Integer hits;
}