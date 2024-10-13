package ru.practicum.ewm.main.api.publicAPI.category.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.publicAPI.category.service.PublicCategoryService;
import ru.practicum.ewm.main.data.constants.DefaultValue;
import ru.practicum.ewm.main.data.dto.category.CategoryDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/categories")
public class PublicCategoryController {
    private final PublicCategoryService publicCategoryService;

    @GetMapping
    public List<CategoryDto> getCategories(@PositiveOrZero
                                           @RequestParam(defaultValue = DefaultValue.ZERO) Integer from,
                                           @Positive
                                           @RequestParam(defaultValue = DefaultValue.SIZE_10) Integer size) {
        return publicCategoryService.getCategories(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable @NotNull Long catId) {
        return publicCategoryService.getCategoryById(catId);
    }
}