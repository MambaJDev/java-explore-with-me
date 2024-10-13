package ru.practicum.ewm.main.api.adminAPI.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.main.data.dto.user.NewUserRequest;
import ru.practicum.ewm.main.data.dto.user.UserDto;
import ru.practicum.ewm.main.data.mapper.user.UserMapper;
import ru.practicum.ewm.main.persistence.model.user.User;
import ru.practicum.ewm.main.persistence.repository.UserRepository;
import ru.practicum.ewm.main.validation.Validation;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final Validation validation;


    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        Pageable page = PageRequest.of(from / size, size);
        if (ids == null) {
            return userRepository.findAll(page).stream()
                    .map(userMapper::userToUserDto)
                    .toList();
        } else {
            return userRepository.findByIdInOrderById(ids, page).stream()
                    .map(userMapper::userToUserDto)
                    .toList();
        }
    }

    @Override
    public UserDto saveUser(NewUserRequest newUserRequest) {
        User user = userMapper.newUserRequestToUser(newUserRequest);
        userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        validation.checkUserExist(userId, userRepository);
        userRepository.deleteById(userId);
    }
}