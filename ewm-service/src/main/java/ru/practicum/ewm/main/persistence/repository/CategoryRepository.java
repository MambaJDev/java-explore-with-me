package ru.practicum.ewm.main.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.persistence.model.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}