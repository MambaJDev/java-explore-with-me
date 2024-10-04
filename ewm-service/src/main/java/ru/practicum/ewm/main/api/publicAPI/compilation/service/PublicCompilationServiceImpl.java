package ru.practicum.ewm.main.api.publicAPI.compilation.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.data.mapper.compilation.CompilationMapper;
import ru.practicum.ewm.main.persistence.model.compilation.Compilation;
import ru.practicum.ewm.main.persistence.repository.CompilationRepository;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        if (pinned == null) {
            return compilationRepository.findAll(pageable).stream()
                    .map(compilationMapper::toCompilationDto)
                    .toList();
        }
        return compilationRepository.findByPinned(pinned, pageable).stream()
                .map(compilationMapper::toCompilationDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format(Constants.COMPILATION_NOT_FOUND, compId)));
        return compilationMapper.toCompilationDto(compilation);
    }
}