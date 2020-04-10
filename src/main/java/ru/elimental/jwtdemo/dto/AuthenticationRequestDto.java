package ru.elimental.jwtdemo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDto {

    private String username;
    private String password;
}
