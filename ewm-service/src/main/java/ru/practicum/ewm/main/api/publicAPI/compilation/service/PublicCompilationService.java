package ru.practicum.ewm.main.api.publicAPI.compilation.service;

import java.util.List;
import ru.practicum.ewm.main.data.dto.compilation.CompilationDto;

public interface PublicCompilationService {

    List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size);

    CompilationDto getCompilationById(Long compId);
}