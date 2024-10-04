package ru.practicum.ewm.main.data.mapper.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.data.dto.category.CategoryDto;
import ru.practicum.ewm.main.data.dto.category.NewCategoryDto;
import ru.practicum.ewm.main.persistence.model.category.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category newCategoryDtoToCategory(NewCategoryDto newCategoryDto);

    CategoryDto categoryToCategoryDto(Category category);
}