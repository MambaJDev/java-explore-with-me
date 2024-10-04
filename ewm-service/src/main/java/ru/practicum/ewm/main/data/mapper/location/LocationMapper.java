package ru.practicum.ewm.main.data.mapper.location;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.main.data.dto.location.NewLocationDto;
import ru.practicum.ewm.main.persistence.model.location.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(target = "id", ignore = true)
    Location newLocationDtoToLocation(NewLocationDto newLocationDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "id", ignore = true)
    void updateLocation(@MappingTarget Location location, NewLocationDto newLocationDto);
}