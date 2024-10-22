package ru.practicum.ewm.main.data.dto.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentUserDto {
    @Size(min = 1, max = 7000)
    private String text;
    @Min(0)
    @Max(10)
    private Integer mark;
}