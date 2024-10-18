package ru.practicum.ewm.main.api.privateAPI.comment.service;

import ru.practicum.ewm.main.data.dto.comment.CommentFullDto;
import ru.practicum.ewm.main.data.dto.comment.NewCommentDto;
import ru.practicum.ewm.main.data.dto.comment.UpdateCommentUserDto;

public interface PrivateCommentService {

    CommentFullDto createComment(Long userId, Long eventId, NewCommentDto commentDto);

    CommentFullDto getComment(Long userId, Long comId);

    CommentFullDto updateComment(Long userId, Long comId, UpdateCommentUserDto updateCommentUserDto);

    void deleteComment(Long userId, Long comId);
}