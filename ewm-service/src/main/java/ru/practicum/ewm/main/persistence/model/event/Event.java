package ru.practicum.ewm.main.persistence.model.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.enums.EventState;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.location.Location;
import ru.practicum.ewm.main.persistence.model.user.User;

@Entity
@Table(name = "events")
@Getter
@Setter
@Accessors(chain = true)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category eventCategory;
    @Column(nullable = false)
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location eventLocation;
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    private String title;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    @Column(name = "published_date", nullable = false)
    private LocalDateTime publishedOn;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests;
    @Column(name = "event_state")
    @Enumerated(EnumType.STRING)
    private EventState state;
    @Transient
    private Long views;
}