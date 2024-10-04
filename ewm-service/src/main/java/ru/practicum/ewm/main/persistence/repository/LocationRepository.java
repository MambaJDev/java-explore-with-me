package ru.practicum.ewm.main.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.persistence.model.location.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}