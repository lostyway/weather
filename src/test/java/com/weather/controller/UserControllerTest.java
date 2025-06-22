package com.weather.controller;

import com.weather.AbstractTest;
import com.weather.dtos.LocationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserControllerTest extends AbstractTest {

    @Autowired
    private UserController userController;

    @Autowired
    private WeatherApiController weatherApiController;

    @Test
    void getUserLoginSuccess() {
        String view = userController.getSignInPage();
        assertThat(view).isEqualTo("sign-in");
    }

    @Test
    void getUserRegister() {
        String view = userController.getSignUpPage();
        assertThat(view).isEqualTo("sign-up");
    }

    @Test
    void whenLoginInClosePage() {
        LocationDto locationDto = new LocationDto();
        locationDto.setCity("Moscow");
        String view = weatherApiController.addLocation(null, locationDto);
        assertThat(view).isEqualTo("redirect:/user/sign-in");
    }

    @Test
    void whenLoginInClosePageWithUser() {
        LocationDto locationDto = new LocationDto();
        locationDto.setCity("Moscow");
        User user = new User("lostway", "123", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String view = weatherApiController.addLocation(user, locationDto);
        assertThat(view).isEqualTo("redirect:/");
    }

    @Test
    void whenLoginInCloseDeletePage() {
        LocationDto locationDto = new LocationDto();
        locationDto.setCity("Moscow");
        String view = weatherApiController.deleteLocation(1L, null);
        assertThat(view).isEqualTo("redirect:/user/sign-in");
    }

    @Test
    void whenLoginInCloseDeletePageWithUser() {
        LocationDto locationDto = new LocationDto();
        locationDto.setCity("Moscow");
        User user = new User("lostway", "123", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String view = weatherApiController.deleteLocation(1L, user);
        assertThat(view).isEqualTo("redirect:/");
    }
}