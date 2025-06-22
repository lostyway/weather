package com.weather.service;

import com.weather.dtos.UserDto;
import com.weather.model.UserEntity;

public interface IUserService {
    void register(UserDto userDto);

    UserEntity findByLogin(String login);
}
