package ru.practicum.ewm.main.api.publicAPI.category.service;

import java.util.List;
import ru.practicum.ewm.main.data.dto.category.CategoryDto;

public interface PublicCategoryService {

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}