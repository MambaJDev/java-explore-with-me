package ru.practicum.ewm.main.data.mapper.comment;

import java.time.LocalDateTime;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.main.data.dto.comment.CommentFullDto;
import ru.practicum.ewm.main.data.dto.comment.CommentShortDto;
import ru.practicum.ewm.main.data.dto.comment.NewCommentDto;
import ru.practicum.ewm.main.data.dto.comment.UpdateCommentUserDto;
import ru.practicum.ewm.main.data.enums.ComStatus;
import ru.practicum.ewm.main.persistence.model.comment.Comment;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.user.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    Comment toComment(NewCommentDto newCommentDto,
                      User commentator,
                      Event event,
                      ComStatus status,
                      LocalDateTime created);


    CommentFullDto toCommentFullDto(Comment comment);

    CommentShortDto toCommentShortDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateComment(@MappingTarget Comment comment, UpdateCommentUserDto updateCommentUserDto);
}