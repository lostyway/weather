package com.weather.service;

import com.weather.dtos.UserDto;
import com.weather.model.User;

public interface IUserService {
    void register(UserDto userDto);

    User findByLogin(String login);
}
