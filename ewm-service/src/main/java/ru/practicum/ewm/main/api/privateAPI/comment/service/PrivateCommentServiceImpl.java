package ru.practicum.ewm.main.api.privateAPI.comment.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.api.errorAPI.BadRequestException;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.comment.CommentFullDto;
import ru.practicum.ewm.main.data.dto.comment.NewCommentDto;
import ru.practicum.ewm.main.data.dto.comment.UpdateCommentUserDto;
import ru.practicum.ewm.main.data.enums.ComStatus;
import ru.practicum.ewm.main.data.enums.ComType;
import ru.practicum.ewm.main.data.mapper.comment.CommentMapper;
import ru.practicum.ewm.main.persistence.model.comment.Comment;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.user.User;
import ru.practicum.ewm.main.persistence.repository.CommentRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.persistence.repository.UserRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;
    private final Validation validation;

    @Override
    public CommentFullDto createComment(Long userId, Long eventId, NewCommentDto commentDto) {

        User commentator = validation.checkUserExist(userId, userRepository);
        Event event = validation.checkEventExist(eventId, eventRepository);
        Comment comment = commentMapper.toComment(
                commentDto,
                commentator,
                event,
                ComStatus.PENDING,
                ComType.PENDING,
                LocalDateTime.now().withNano(0)
        );
        commentRepository.save(comment);
        return commentMapper.toCommentFullDto(comment);
    }

    @Override
    public CommentFullDto getComment(Long userId, Long comId) {
        Comment comment = checkCommentExist(comId);
        checkRequesterExistAndIsCommentator(userId, comment);
        return commentMapper.toCommentFullDto(comment);
    }

    @Override
    public CommentFullDto updateComment(Long userId, Long comId, UpdateCommentUserDto updateCommentUserDto) {
        Comment comment = checkCommentExist(comId);
        checkRequesterExistAndIsCommentator(userId, comment);
        commentMapper.updateComment(comment, updateCommentUserDto);
        commentRepository.save(comment);
        return commentMapper.toCommentFullDto(comment);
    }

    @Override
    public void deleteComment(Long userId, Long comId) {
        Comment comment = checkCommentExist(comId);
        checkRequesterExistAndIsCommentator(userId, comment);
        commentRepository.delete(comment);
    }

    private Comment checkCommentExist(Long comId) {
        return commentRepository.findById(comId).orElseThrow(
                () -> new NotFoundException(String.format(Constants.COMMENT_NOT_FOUND, comId)));
    }

    private void checkRequesterExistAndIsCommentator(Long userId, Comment comment) {
        User commentator = validation.checkUserExist(userId, userRepository);
        if (!commentator.equals(comment.getCommentator())) {
            throw new BadRequestException(String.format(Constants.REQUESTER_NOT_COMMENTATOR, userId));
        }
    }
}