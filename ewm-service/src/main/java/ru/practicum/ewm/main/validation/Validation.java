package ru.practicum.ewm.main.validation;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.api.errorAPI.BadRequestException;
import ru.practicum.ewm.main.api.errorAPI.NotFoundException;
import ru.practicum.ewm.main.api.errorAPI.WrongEnumException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.enums.RequestStatus;
import ru.practicum.ewm.main.data.enums.Sort;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.model.compilation.Compilation;
import ru.practicum.ewm.main.persistence.model.event.Event;
import ru.practicum.ewm.main.persistence.model.participation.ParticipationRequest;
import ru.practicum.ewm.main.persistence.model.user.User;
import ru.practicum.ewm.main.persistence.repository.CategoryRepository;
import ru.practicum.ewm.main.persistence.repository.CompilationRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.persistence.repository.ParticipationRepository;
import ru.practicum.ewm.main.persistence.repository.UserRepository;

@Component
public class Validation {

    public void validateDates(LocalDateTime start, LocalDateTime end) {
        if (start != null && end != null && end.isBefore(start)) {
            throw new BadRequestException(Constants.END_BEFORE_START);
        }
    }

    public void validateEnums(Sort sort) {
        if (!Arrays.asList(Sort.values()).contains(sort)) {
            throw new WrongEnumException(Constants.INVALID_SORT);
        }
    }

    public User checkUserExist(Long userId, UserRepository userRepository) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Constants.USER_NOT_FOUND, userId)));
    }

    public Event checkEventExist(Long eventId, EventRepository eventRepository) {
        return eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.EVENT_NOT_FOUND, eventId)));
    }

    public ParticipationRequest checkParticipationExist(Long partId, ParticipationRepository participationRepository) {
        return participationRepository.findById(partId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.PARTICIPATION_NOT_FOUND, partId)));
    }

    public Compilation checkCompilationExist(Long compId, CompilationRepository compilationRepository) {
        return compilationRepository.findById(compId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.COMPILATION_NOT_FOUND, compId)));
    }

    public Category checkCategoryExist(Long catId, CategoryRepository categoryRepository) {
        return categoryRepository.findById(catId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.CATEGORY_NOT_FOUND, catId)));
    }

    public void checkPendingRequestStatus(List<ParticipationRequest> participationRequestRequestList) {
        participationRequestRequestList.forEach(participation -> {
            if (!participation.getRequestStatus().equals(RequestStatus.PENDING)) {
                throw new IllegalArgumentException(Constants.NOT_PENDING);
            }
        });
    }
}