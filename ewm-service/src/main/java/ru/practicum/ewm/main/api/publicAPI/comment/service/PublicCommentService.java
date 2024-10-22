package ru.practicum.ewm.main.api.publicAPI.comment.service;

import java.util.List;
import ru.practicum.ewm.main.data.dto.comment.CommentShortDto;

public interface PublicCommentService {

    List<CommentShortDto> getCommentsByEventId(Long eventId, String sort, Integer from, Integer size);
}