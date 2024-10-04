package ru.practicum.ewm.server.stats.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.server.stats.model.EndpointHit;
import ru.practicum.ewm.server.stats.model.StatDataView;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {


    @Query("select new ru.practicum.ewm.server.stats.model.StatDataView(e.app, e.uri, count(e.ip)) from EndpointHit e " +
            "where e.timestamp >= ?1 and e.timestamp <= ?2 group by e.app, e.uri order by count(e.ip) DESC")
    List<StatDataView> getAllStatDataView(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.ewm.server.stats.model.StatDataView(e.app, e.uri, count(distinct e.ip)) from EndpointHit e " +
            "where e.timestamp >= ?1 and e.timestamp <= ?2 group by e.app, e.uri order by count(distinct e.ip) DESC")
    List<StatDataView> getAllStatDataViewWithDistinctIp(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.ewm.server.stats.model.StatDataView(e.app, e.uri, count(e.ip)) from EndpointHit e " +
            "where e.uri in ?1 and e.timestamp >= ?2 and e.timestamp <= ?3 group by e.app, e.uri order by count(e.ip) DESC")
    List<StatDataView> getAllStatDataViewInUris(String[] uris, LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.ewm.server.stats.model.StatDataView(e.app, e.uri, count(distinct e.ip)) from EndpointHit e " +
            "where e.uri in ?1 and e.timestamp >= ?2 and e.timestamp <= ?3 group by e.app, e.uri order by count(distinct e.ip) DESC")
    List<StatDataView> getAllStatDataViewInUrisAndDistinctIp(String[] uris, LocalDateTime start, LocalDateTime end);
}