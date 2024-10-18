package ru.practicum.ewm.main.persistence.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.data.enums.ComStatus;
import ru.practicum.ewm.main.persistence.model.comment.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByCommentatorId(Long id, Pageable pageable);

    List<Comment> findByEventIdAndStatusOrderByCreatedDesc(Long id, ComStatus status, Pageable pageable);
}