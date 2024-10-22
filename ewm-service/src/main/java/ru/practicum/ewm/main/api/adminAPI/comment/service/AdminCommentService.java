package ru.practicum.ewm.main.api.adminAPI.comment.service;

import ru.practicum.ewm.main.data.dto.comment.CommentFullDto;

public interface AdminCommentService {

    CommentFullDto getComment(Long comId);

    void deleteCommentById(Long comId);

    CommentFullDto updateCommentStatus(Long comId);
}