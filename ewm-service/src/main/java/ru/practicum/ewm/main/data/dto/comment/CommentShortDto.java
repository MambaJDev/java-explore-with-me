package ru.practicum.ewm.main.data.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.dto.user.UserShortDto;

@Getter
@Setter
public class CommentShortDto {
    private Long id;
    private String text;
    private UserShortDto commentator;
    private EventShortDto event;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;
}