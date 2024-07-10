package com.sparta.millboard.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private String username;

    private String accessToken;

    public UserResponseDto(String username) {
        this.username = username;
    }
}
