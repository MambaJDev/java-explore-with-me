package ru.practicum.ewm.main.persistence.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.persistence.model.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdInOrderById(List<Long> ids, Pageable pageable);
}