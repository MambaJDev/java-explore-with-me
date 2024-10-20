package ru.practicum.ewm.main.api.adminAPI.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.comment.CommentFullDto;
import ru.practicum.ewm.main.data.enums.ComStatus;
import ru.practicum.ewm.main.data.mapper.comment.CommentMapper;
import ru.practicum.ewm.main.persistence.model.comment.Comment;
import ru.practicum.ewm.main.persistence.repository.CommentRepository;

@Service
@AllArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private static final int AVERAGE_RATING = 5;

    @Override
    public CommentFullDto getComment(Long comId) {
        Comment comment = checkCommentExist(comId);
        return commentMapper.toCommentFullDto(comment);
    }

    @Override
    public void deleteCommentById(Long comId) {
        Comment comment = checkCommentExist(comId);
        commentRepository.delete(comment);
    }

    @Override
    public CommentFullDto updateCommentStatus(Long comId) {
        Comment comment = checkCommentExist(comId);
        if (!comment.getStatus().equals(ComStatus.PENDING)) {
            throw new IllegalArgumentException(Constants.NOT_PENDING_COMMENT);
        }
        if (comment.getMark() > AVERAGE_RATING) {
            comment.setStatus(ComStatus.PUBLISHED_POSITIVE);
        } else {
            comment.setStatus(ComStatus.PUBLISHED_NEGATIVE);
        }
        commentRepository.save(comment);
        return commentMapper.toCommentFullDto(comment);
    }

    private Comment checkCommentExist(Long comId) {
        return commentRepository.findById(comId).orElseThrow(
                () -> new NotFoundException(String.format(Constants.COMMENT_NOT_FOUND, comId)));
    }
}