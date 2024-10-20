package ru.practicum.ewm.main.api.publicAPI.comment.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.data.dto.comment.CommentShortDto;
import ru.practicum.ewm.main.data.enums.ComSort;
import ru.practicum.ewm.main.data.enums.ComStatus;
import ru.practicum.ewm.main.data.mapper.comment.CommentMapper;
import ru.practicum.ewm.main.persistence.model.comment.Comment;
import ru.practicum.ewm.main.persistence.repository.CommentRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final Validation validation;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommentShortDto> getCommentsByEventId(Long eventId, String sort, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        validation.checkEventExist(eventId, eventRepository);
        List<Comment> comments = commentRepository.findByEventIdOrderByCreatedDesc(eventId, pageable);
        if (comments.isEmpty()) {
            return Collections.emptyList();
        }
        ComSort comSort = Enum.valueOf(ComSort.class, sort);
        if (comSort.equals(ComSort.OLD_DATE)) {
            comments = comments.stream()
                    .sorted(Comparator.comparing(Comment::getCreated))
                    .toList();
        }
        if (comSort.equals(ComSort.NEGATIVE)) {
            comments = comments.stream()
                    .filter(comment -> comment.getStatus().equals(ComStatus.PUBLISHED_NEGATIVE))
                    .toList();
        }
        if (comSort.equals(ComSort.POSITIVE)) {
            comments = comments.stream()
                    .filter(comment -> comment.getStatus().equals(ComStatus.PUBLISHED_POSITIVE))
                    .toList();
        }
        return comments.stream()
                .map(commentMapper::toCommentShortDto)
                .toList();
    }
}