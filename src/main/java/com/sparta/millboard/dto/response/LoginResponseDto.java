package com.sparta.millboard.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String username;

    private String accessToken;

    public LoginResponseDto(String username, String accessToken) {
        this.username = username;
        this.accessToken = accessToken;
    }
}
