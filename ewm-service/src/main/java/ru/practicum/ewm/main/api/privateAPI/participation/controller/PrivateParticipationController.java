package ru.practicum.ewm.main.api.privateAPI.participation.controller;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.privateAPI.participation.service.ParticipationPrivateService;
import ru.practicum.ewm.main.data.dto.participation.ParticipationRequestDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users/{userId}/requests")
public class PrivateParticipationController {
    private final ParticipationPrivateService participationPrivateService;

    @GetMapping
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable @NotNull Long userId) {
        return participationPrivateService.getParticipationRequests(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto sendParticipationRequest(@PathVariable @NotNull Long userId,
                                                            @RequestParam @NotNull Long eventId) {
        return participationPrivateService.sendParticipationRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable @NotNull Long userId,
                                                              @PathVariable @NotNull Long requestId) {
        return participationPrivateService.cancelParticipationRequest(userId, requestId);
    }
}