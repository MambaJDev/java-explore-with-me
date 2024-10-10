package ru.practicum.ewm.main.data.dto.category;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class CategoryDto {
    private Long id;
    @Size(min = 1, max = 50)
    private String name;
}