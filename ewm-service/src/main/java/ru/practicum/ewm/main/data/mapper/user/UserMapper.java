package ru.practicum.ewm.main.data.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.ewm.main.data.dto.user.NewUserRequest;
import ru.practicum.ewm.main.data.dto.user.UserDto;
import ru.practicum.ewm.main.persistence.model.user.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User newUserRequestToUser(NewUserRequest newUserRequest);

    UserDto userToUserDto(User user);
}