package ru.practicum.ewm.main.api.adminAPI.compilation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.data.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.data.mapper.compilation.CompilationMapper;
import ru.practicum.ewm.main.persistence.model.compilation.Compilation;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.repository.CompilationRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final CompilationMapper compilationMapper;
    private final Validation validation;

    @Override
    public CompilationDto addCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = compilationMapper.toCompilation(newCompilationDto);
        if (newCompilationDto.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
            compilation.setEvents(events);
        }
        compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(compilation);
    }

    @Override
    public void deleteCompilationById(Long compId) {
        validation.checkCompilationExist(compId, compilationRepository);
        compilationRepository.deleteById(compId);
    }

    @Override
    public CompilationDto updateCompilationById(Long compId, UpdateCompilationRequest compilationRequest) {
        Compilation compilation = validation.checkCompilationExist(compId, compilationRepository);
        if (compilationRequest.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(compilationRequest.getEvents());
            compilation.setEvents(events);
        }
        compilationMapper.update(compilation, compilationRequest);
        compilationRepository.save(compilation);
        return compilationMapper.toCompilationDto(compilation);
    }
}