package com.sparta.millboard.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequestDto {

    private String username;

    private String password;

    private String newUsername;

    private String newPassword;

}
