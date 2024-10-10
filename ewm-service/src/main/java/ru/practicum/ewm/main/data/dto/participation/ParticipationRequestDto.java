package ru.practicum.ewm.main.data.dto.participation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.enums.RequestStatus;

@Getter
@Setter
@Accessors(chain = true)
public class ParticipationRequestDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_FORMAT)
    private LocalDateTime created;
    private Long event;
    private Long requester;
    private RequestStatus status;
}