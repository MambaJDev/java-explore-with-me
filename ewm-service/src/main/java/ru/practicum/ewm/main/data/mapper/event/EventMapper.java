package ru.practicum.ewm.main.data.mapper.event;

import java.time.LocalDateTime;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.EventShortDto;
import ru.practicum.ewm.main.data.dto.event.NewEventDto;
import ru.practicum.ewm.main.data.dto.event.UpdateEventAdminRequest;
import ru.practicum.ewm.main.data.dto.event.UpdateEventUserRequest;
import ru.practicum.ewm.main.data.enums.EventState;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.location.Location;
import ru.practicum.ewm.main.persistence.model.user.User;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "eventCategory", source = "categoryWithId")
    @Mapping(target = "eventLocation", source = "locationWithId")
    Event toEvent(NewEventDto newEventDto,
                  Location locationWithId,
                  Category categoryWithId,
                  User initiator,
                  LocalDateTime createdOn,
                  EventState state,
                  Integer confirmedRequests,
                  Long views
    );

    @Mapping(target = "location", source = "eventLocation")
    @Mapping(target = "category", source = "eventCategory")
    EventFullDto toEventFullDto(Event event);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventCategory", ignore = true)
    @Mapping(target = "eventLocation", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    void updateEventByAdmin(@MappingTarget Event event, UpdateEventAdminRequest updateEventAdminRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "views", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "eventCategory", ignore = true)
    @Mapping(target = "eventLocation", ignore = true)
    @Mapping(target = "initiator", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "confirmedRequests", ignore = true)
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", ignore = true)
    void updateEventByPrivate(@MappingTarget Event event, UpdateEventUserRequest updateEventUserRequest);

    @Mapping(target = "category", source = "event.eventCategory")
    EventShortDto toEventShortDto(Event event);
}