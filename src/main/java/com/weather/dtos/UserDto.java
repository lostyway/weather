package com.weather.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @Length(min = 3, max = 55)
    private String username;

    @Length(min = 3, max = 200)
    private String password;

    @Length(min = 3, max = 200)
    private String repeatPassword;
}
