package ru.practicum.ewm.main.data.dto.event;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import ru.practicum.ewm.main.data.enums.RequestStatus;

@Getter
@NotNull
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;
    @NotNull
    private RequestStatus status;
}