package ru.practicum.ewm.dto.stats;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class StatResponseDto {
    private String app;
    private String uri;
    private Long hits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatResponseDto that = (StatResponseDto) o;
        return Objects.equals(hits, that.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hits);
    }
}