package ru.practicum.ewm.main.api.adminAPI.category.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.api.adminAPI.category.service.AdminCategoryService;
import ru.practicum.ewm.main.data.dto.category.CategoryDto;
import ru.practicum.ewm.main.data.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.data.dto.category.UpdateCategoryDto;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return adminCategoryService.add(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable @NotNull Long catId) {
        adminCategoryService.delete(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategoryById(@PathVariable @NotNull Long catId,
                                          @RequestBody @Valid UpdateCategoryDto updateCategoryDto) {
        return adminCategoryService.update(catId, updateCategoryDto);
    }
}