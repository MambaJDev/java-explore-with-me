package ru.practicum.ewm.main.data.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {
    private Long id;
    @Email
    @NotBlank
    private String name;
    @NotBlank
    private String email;
}