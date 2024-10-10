package ru.practicum.ewm.main.persistence.model.participation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.practicum.ewm.main.data.enums.RequestStatus;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.user.User;

@Entity
@Table(name = "participation_requests")
@Getter
@Setter
@Accessors(chain = true)
public class ParticipationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Column(name = "status")
    private RequestStatus requestStatus = RequestStatus.PENDING;
}