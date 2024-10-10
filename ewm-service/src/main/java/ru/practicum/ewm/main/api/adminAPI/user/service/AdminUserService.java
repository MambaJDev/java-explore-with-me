package ru.practicum.ewm.main.api.adminAPI.user.service;

import java.util.List;
import ru.practicum.ewm.main.data.dto.user.NewUserRequest;
import ru.practicum.ewm.main.data.dto.user.UserDto;

public interface AdminUserService {

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserDto saveUser(NewUserRequest newUserRequest);

    void deleteUser(Long userId);
}