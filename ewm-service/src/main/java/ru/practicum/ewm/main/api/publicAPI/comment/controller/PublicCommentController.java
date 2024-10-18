package ru.practicum.ewm.main.api.publicAPI.comment.controller;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.publicAPI.comment.service.PublicCommentService;
import ru.practicum.ewm.main.data.constants.DefaultValue;
import ru.practicum.ewm.main.data.dto.comment.CommentShortDto;

@RestController
@RequestMapping(path = "/comments")
@RequiredArgsConstructor
@Validated
public class PublicCommentController {
    private final PublicCommentService publicCommentService;

    @GetMapping("/{eventId}")
    public List<CommentShortDto> getCommentsByEventId(@PathVariable @NotNull Long eventId,
                                                      @RequestParam(defaultValue = "NEW_DATE") String sort,
                                                      @RequestParam(defaultValue = DefaultValue.ZERO) Integer from,
                                                      @RequestParam(defaultValue = DefaultValue.SIZE_10) Integer size) {
        return publicCommentService.getCommentsByEventId(eventId, sort, from, size);
    }
}