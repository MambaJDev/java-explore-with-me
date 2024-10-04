package ru.practicum.ewm.main.api.adminAPI.category.service;

import ru.practicum.ewm.main.data.dto.category.CategoryDto;
import ru.practicum.ewm.main.data.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.data.dto.category.UpdateCategoryDto;

public interface AdminCategoryService {

    CategoryDto add(NewCategoryDto newCategoryDto);

    void delete(Long catId);

    CategoryDto update(Long catId, UpdateCategoryDto updateCategoryDto);
}