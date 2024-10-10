package ru.practicum.ewm.main.data.dto.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.user.UserShortDto;
import ru.practicum.ewm.main.data.enums.EventState;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.location.Location;

@Getter
@Setter
@Accessors(chain = true)
public class EventFullDto {
    private Long id;
    private String title;
    private String annotation;
    private Category category;
    private Boolean paid;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime eventDate;
    private UserShortDto initiator;
    private String description;
    private Integer participantLimit;
    private EventState state;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime createdOn;
    private Location location;
    private Boolean requestModeration;
    private Integer confirmedRequests;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime publishedOn;
    private Long views;
}