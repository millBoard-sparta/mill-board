package com.sparta.millboard.dto.response;

import com.sparta.millboard.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {
    private Long id;

    private String username;

    private User.Role role;

    public UserResponseDto(String username) {
        this.username = username;
    }

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.role = user.getRole();
    }
}
