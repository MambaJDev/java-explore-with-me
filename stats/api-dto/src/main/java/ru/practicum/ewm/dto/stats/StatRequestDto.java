package ru.practicum.ewm.dto.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.dto.constant.Constants;

@Getter
@Setter
@Accessors(chain = true)
public class StatRequestDto {
    @NonNull
    private String app;
    @NonNull
    private String ip;
    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime timestamp;
    private String uri;
}