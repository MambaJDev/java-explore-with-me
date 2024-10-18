package ru.practicum.ewm.main.api.privateAPI.comment.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.privateAPI.comment.service.PrivateCommentService;
import ru.practicum.ewm.main.data.dto.comment.CommentFullDto;
import ru.practicum.ewm.main.data.dto.comment.NewCommentDto;
import ru.practicum.ewm.main.data.dto.comment.UpdateCommentUserDto;

@RestController
@RequestMapping(path = "/users/{userId}/comments")
@RequiredArgsConstructor
@Validated
public class PrivateCommentController {
    private final PrivateCommentService privateCommentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CommentFullDto createComment(@PathVariable @NotNull Long userId,
                                 @RequestParam @NotNull Long eventId,
                                 @RequestBody @Valid NewCommentDto newCommentDto) {
        return privateCommentService.createComment(userId, eventId, newCommentDto);
    }

    @GetMapping("/{comId}")
    CommentFullDto getComment(@PathVariable @NotNull Long userId,
                              @PathVariable @NotNull Long comId) {
        return privateCommentService.getComment(userId, comId);
    }

    @DeleteMapping("/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteComment(@PathVariable @NotNull Long userId, @PathVariable @NotNull Long comId) {
        privateCommentService.deleteComment(userId, comId);
    }

    @PatchMapping("/{comId}")
    CommentFullDto updateComment(@PathVariable @NotNull Long userId,
                                 @PathVariable @NotNull Long comId,
                                 @RequestBody @Valid UpdateCommentUserDto updateCommentUserDto) {
        return privateCommentService.updateComment(userId, comId, updateCommentUserDto);
    }
}