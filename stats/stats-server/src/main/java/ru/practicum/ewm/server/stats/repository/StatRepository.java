package ru.practicum.ewm.server.stats.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.server.stats.model.EndpointHit;
import ru.practicum.ewm.server.stats.model.ShortEndpointHit;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "select distinct e.app, e.uri from endpoint_hits as e where e.uri in ?1 and e.create_date between ?2 and ?3", nativeQuery = true)
    List<ShortEndpointHit> getAllStatsFromIpSet(Set<String> uris,
                                                      LocalDateTime start,
                                                      LocalDateTime end
    );

    @Query(value = "select distinct e.app, e.uri from endpoint_hits as e where e.create_date between ?1 and ?2", nativeQuery = true)
    List<ShortEndpointHit> getAllStats(LocalDateTime start,
                                       LocalDateTime end
    );

    Integer countByAppAndUriAndTimestampBetween(String app,
                                                      String uri,
                                                      LocalDateTime start,
                                                      LocalDateTime end);

    @Query(value = "select count(distinct ip) from endpoint_hits where app =? and uri=? and create_date  between ? and ?",
            nativeQuery = true)
    Integer countByAppAndUriAndTimestampBetweenWhenIpIsUnique(String app,
                                                        String uri,
                                                        LocalDateTime start,
                                                        LocalDateTime end);
}