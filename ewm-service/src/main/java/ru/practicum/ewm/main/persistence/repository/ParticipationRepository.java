package ru.practicum.ewm.main.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.persistence.model.participation.ParticipationRequest;

public interface ParticipationRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByRequesterIdOrderByCreatedDesc(Long id);

    List<ParticipationRequest> findByEventIdOrderByCreatedDesc(Long id);

    ParticipationRequest findByRequesterIdAndEventId(Long requesterId, Long eventId);
}