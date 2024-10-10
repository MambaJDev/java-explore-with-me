package ru.practicum.ewm.main.api.adminAPI.event.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.adminAPI.event.service.EventAdminService;
import ru.practicum.ewm.main.data.dto.event.EventFullDto;
import ru.practicum.ewm.main.data.dto.event.UpdateEventAdminRequest;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/events")
public class AdminEventController {
    private final EventAdminService eventAdminService;


    @GetMapping
    List<EventFullDto> getEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                        @RequestParam(required = false) List<String> states,
                                        @RequestParam(required = false) List<Long> categories,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(required = false) LocalDateTime rangeStart,
                                        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                        @RequestParam(required = false) LocalDateTime rangeEnd,
                                        @PositiveOrZero
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @Positive
                                        @RequestParam(defaultValue = "10") Integer size) {

        return eventAdminService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable @NotNull Long eventId,
                                           @RequestBody @Valid UpdateEventAdminRequest request) {
        return eventAdminService.updateEventByAdmin(eventId, request);
    }
}