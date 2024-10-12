package ru.practicum.ewm.main.validation;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.user.User;
import ru.practicum.ewm.main.persistence.repository.CategoryRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.persistence.repository.UserRepository;

@Component
public class Validation {

    public Event checkEventExist(Long eventId, EventRepository eventRepository) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.EVENT_NOT_FOUND, eventId)));
    }

    public User checkUserExist(Long userId, UserRepository userRepository) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Constants.USER_NOT_FOUND, userId)));
    }

    public Category checkCategoryExist(Long catId, CategoryRepository categoryRepository) {
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.CATEGORY_NOT_FOUND, catId)));
    }
}