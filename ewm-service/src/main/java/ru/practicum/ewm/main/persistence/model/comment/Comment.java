package ru.practicum.ewm.main.persistence.model.comment;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.enums.ComStatus;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.user.User;

@Entity
@Table(name = "comments")
@Getter
@Setter
@Accessors(chain = true)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String text;
    @ManyToOne
    @JoinColumn(name = "commentator_id", nullable = false)
    private User commentator;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ComStatus status;
    @NotNull
    private Integer mark;
    @NotNull
    private LocalDateTime created;
}