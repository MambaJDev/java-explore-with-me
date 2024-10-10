package ru.practicum.ewm.main.data.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.user.UserShortDto;
import ru.practicum.ewm.main.persistence.model.category.Category;

@Getter
@Setter
@Accessors(chain = true)
public class EventShortDto {
    private Long id;
    private String annotation;
    private Category category;
    private Integer confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private Long views;
}