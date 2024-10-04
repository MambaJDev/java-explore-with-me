package ru.practicum.ewm.main.data.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class NewUserRequest {
    @Email
    @Size(min = 6, max = 254)
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}