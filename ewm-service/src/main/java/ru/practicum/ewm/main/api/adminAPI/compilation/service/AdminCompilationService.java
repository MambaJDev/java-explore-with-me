package ru.practicum.ewm.main.api.adminAPI.compilation.service;

import ru.practicum.ewm.main.data.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.UpdateCompilationRequest;

public interface AdminCompilationService {

    CompilationDto addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilationById(Long compId);

    CompilationDto updateCompilationById(Long compId, UpdateCompilationRequest compilationRequest);
}