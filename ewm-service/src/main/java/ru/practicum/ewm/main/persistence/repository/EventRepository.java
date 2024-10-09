package ru.practicum.ewm.main.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.event.Event;


public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select * from events as e where " +
            "(cast(:users as BIGINT) is null or e.initiator_id in (cast(:users as BIGINT))) " +
            "and (cast(:states as TEXT) is null or e.event_state in (cast(:states as TEXT))) " +
            "and (cast(:categories as BIGINT) is null or e.category_id in (cast(:categories as BIGINT))) " +
            "and (cast(:rangeStart as TIMESTAMP) is null or e.event_date >= cast(:rangeStart as TIMESTAMP)) " +
            "and (cast(:rangeEnd as TIMESTAMP) is null or e.event_date <= cast(:rangeEnd  as TIMESTAMP)) ", nativeQuery = true)
    List<Event> getAllEventsByAdmin(@Param("users") List<Long> users,
                                    @Param("states") List<String> states,
                                    @Param("categories") List<Long> categories,
                                    @Param("rangeStart") LocalDateTime rangeStart,
                                    @Param("rangeEnd") LocalDateTime rangeEnd,
                                    Pageable pageable);


    @Query(value = "select * from events as e where e.event_state = 'PUBLISHED' " +
            "and (cast(:text as TEXT) is null or e.annotation ilike concat('%',cast(:text as TEXT),'%') " +
            "or e.description ilike concat('%',cast(:text as TEXT),'%')) " +
            "and (cast(:categories as BIGINT) is null or e.category_id in (cast(:categories as BIGINT))) " +
            "and (cast(:paid as BOOLEAN) is null or e.paid = cast(:paid as BOOLEAN)) " +
            "and (e.event_date between cast(:rangeStart as TIMESTAMP) and cast(:rangeEnd as TIMESTAMP))", nativeQuery = true)
    List<Event> getPublicEvents(@Param("text") String text,
                                @Param("categories") List<Long> categories,
                                @Param("paid") Boolean paid,
                                @Param("rangeStart") LocalDateTime rangeStart,
                                @Param("rangeEnd") LocalDateTime rangeEnd,
                                Pageable pageable);

    List<Event> findAllByInitiatorId(Long id, Pageable pageable);

    List<Event> findALLByEventCategoryId(Long catId);
}