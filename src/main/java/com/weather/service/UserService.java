package com.weather.service;

import com.weather.dtos.UserDto;
import com.weather.model.UserEntity;
import com.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void register(UserDto userDto) {
        validateUserData(userDto);
        UserEntity user = UserEntity.builder()
                .login(userDto.getUsername())
                .password(encoder.encode(userDto.getPassword()))
                .build();
        userRepository.save(user);
    }

    private void validateUserData(UserDto userDto) throws UsernameNotFoundException, IllegalArgumentException {
        if (!Objects.equals(userDto.getPassword(), userDto.getRepeatPassword())) {
            throw new IllegalArgumentException("Пароли не совпадают");
        }

        try {
            UserEntity userFromBd = findByLogin(userDto.getUsername());
            if (userFromBd != null) {
                throw new IllegalArgumentException("Пользователь уже существует");
            }
        } catch (UsernameNotFoundException ignored) {
            //съедаем исключение
        }
    }

    @Override
    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь '%s' не был найден".formatted(login)));
    }
}
