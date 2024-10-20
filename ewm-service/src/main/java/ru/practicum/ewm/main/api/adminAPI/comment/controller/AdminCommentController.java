package ru.practicum.ewm.main.api.adminAPI.comment.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.adminAPI.comment.service.AdminCommentService;
import ru.practicum.ewm.main.data.dto.comment.CommentFullDto;

@RestController
@RequestMapping(path = "/admin/comments/{comId}")
@RequiredArgsConstructor
@Validated
public class AdminCommentController {
    private final AdminCommentService adminCommentService;

    @GetMapping
    public CommentFullDto getComment(@PathVariable @NotNull Long comId) {
        return adminCommentService.getComment(comId);
    }

    @DeleteMapping
    public void deleteCommentById(@PathVariable @NotNull Long comId) {
        adminCommentService.deleteCommentById(comId);
    }

    @PatchMapping
    public CommentFullDto updateCommentStatus(@PathVariable @NotNull Long comId) {
        return adminCommentService.updateCommentStatus(comId);
    }
}