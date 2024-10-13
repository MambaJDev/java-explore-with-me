package ru.practicum.ewm.main.data.dto.compilation;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;

@Getter
@Setter
@Accessors(chain = true)
public class CompilationDto {
    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}