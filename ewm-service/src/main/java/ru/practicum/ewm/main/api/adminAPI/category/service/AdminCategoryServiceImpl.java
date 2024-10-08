package ru.practicum.ewm.main.api.adminAPI.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.api.errorAPI.NotEmptyCategoryException;
import ru.practicum.ewm.main.data.constants.Constants;
import ru.practicum.ewm.main.data.dto.category.CategoryDto;
import ru.practicum.ewm.main.data.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.data.dto.category.UpdateCategoryDto;
import ru.practicum.ewm.main.data.mapper.category.CategoryMapper;
import ru.practicum.ewm.main.persistence.model.category.Category;
import ru.practicum.ewm.main.persistence.repository.CategoryRepository;
import ru.practicum.ewm.main.persistence.repository.EventRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;
    private final Validation validation;

    @Override
    public CategoryDto add(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(categoryMapper.newCategoryDtoToCategory(newCategoryDto));
        return categoryMapper.categoryToCategoryDto(category);
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        Category category = validation.checkCategoryExist(catId,categoryRepository);
        if (eventRepository.findALLByEventCategory(category).isEmpty()) {
            categoryRepository.deleteById(catId);
        } else {
            throw new NotEmptyCategoryException(Constants.BUSY_CATEGORY);
        }
    }

    @Override
    public CategoryDto update(Long catId, UpdateCategoryDto updateCategoryDto) {
        Category category = validation.checkCategoryExist(catId,categoryRepository);
        category.setName(updateCategoryDto.getName());
        categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDto(category);
    }
}