package ru.practicum.ewm.main.api.publicAPI.category.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.data.dto.category.CategoryDto;
import ru.practicum.ewm.main.data.mapper.category.CategoryMapper;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.repository.CategoryRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final Validation validation;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from / size, size);
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::categoryToCategoryDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long catId) {
        Category category = validation.checkCategoryExist(catId, categoryRepository);
        return categoryMapper.categoryToCategoryDto(category);
    }
}