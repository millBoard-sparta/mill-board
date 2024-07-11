package com.sparta.millboard.dto.response;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String username;

    private String accessToken;

    private String refreshToken;

    public LoginResponseDto(String username, String accessToken, String refreshToken) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
