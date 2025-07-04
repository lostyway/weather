package com.weather.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/user/**",       // разрешаем всё под /user (sign-in, sign-up)
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/static/**"
                        ).permitAll()
                        .requestMatchers(
                                "/locations/add",
                                "/locations/delete/{id}"
                        ).authenticated()
                        .anyRequest().permitAll())
                .formLogin(form -> form
                        .loginPage("/user/sign-in")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/user/sign-in?error")
                        .permitAll())
                .csrf(csrf -> csrf.disable())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
