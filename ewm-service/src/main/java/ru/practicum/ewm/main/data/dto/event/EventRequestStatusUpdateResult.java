package ru.practicum.ewm.main.data.dto.event;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;


@Setter
@Getter
@Accessors(chain = true)
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}