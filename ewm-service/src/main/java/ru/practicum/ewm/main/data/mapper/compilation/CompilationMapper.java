package ru.practicum.ewm.main.data.mapper.compilation;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.main.data.dto.compilation.CompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.NewCompilationDto;
import ru.practicum.ewm.main.data.dto.compilation.UpdateCompilationRequest;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.persistence.model.compilation.Compilation;
import ru.practicum.ewm.main.persistence.model.event.Event;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "id", ignore = true)
    Compilation toCompilation(NewCompilationDto newCompilationDto);

    @Mapping(target = "category", source = "event.eventCategory")
    EventShortDto toEventShortDto(Event event);

    CompilationDto toCompilationDto(Compilation compilation);

    @Mapping(target = "events", ignore = true)
    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget Compilation compilation, UpdateCompilationRequest updateCompilationRequest);
}