package com.weather.controller;

import com.weather.AbstractTest;
import com.weather.model.Location;
import com.weather.model.UserEntity;
import com.weather.service.LocationService;
import com.weather.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

class WeatherApiControllerTest extends AbstractTest {

    @Autowired
    private WeatherApiController weatherApiController;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private LocationService locationService;

    @Test
    void getWeatherPage() {
        Model model = new ConcurrentModel();
        User user = new User("lostway", "123", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        UserEntity userEntity = UserEntity.builder()
                .login(user.getUsername())
                .password(user.getPassword())
                .build();


        when(userService.findByLogin(user.getUsername())).thenReturn(userEntity);
        when(locationService.getLocationsByUser(userEntity))
                .thenReturn(List.of(new Location(1, "Krasnodar", 45.0328, 38.9769, userEntity)));

        String view = weatherApiController.showUserLocations(model, user);
        assertThat(view).isEqualTo("index");
        assertThat(model.getAttribute("locations")).isNotNull();
        var list = (List<Location>) model.getAttribute("locations");
        assertThat(list).hasSize(1);
    }

    @Test
    void whenGetWeatherPageWithWrongUser() {
        Model model = new ConcurrentModel();

        String login = "test";
        User user = new User(login, "123", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(userService.findByLogin(login))
                .thenThrow(new UsernameNotFoundException("Пользователь '%s' не был найден".formatted(login)));

        assertThatThrownBy(() -> weatherApiController.showUserLocations(model, user))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining(login);
    }

    @Test
    void whenGetWeatherPageWithEmptyLocations() {
        Model model = new ConcurrentModel();

        User user = new User("test", "123", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        String view = weatherApiController.showUserLocations(model, user);

        assertThat(view).isEqualTo("index");
        assertThat(model.getAttribute("locations")).isEqualTo(List.of());
    }
}