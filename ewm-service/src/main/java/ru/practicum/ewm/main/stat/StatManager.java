package ru.practicum.ewm.main.stat;

import jakarta.servlet.http.HttpServletRequest;
import ru.practicum.ewm.main.persistence.model.event.Event;

public interface StatManager {
    void sendHitEventData(HttpServletRequest request);

    long getUniqueEventViews(HttpServletRequest request, Event event);
}