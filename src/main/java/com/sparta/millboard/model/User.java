package com.sparta.millboard.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String refreshToken; // uuid

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        USER,
        MANAGER
    }

    @Builder
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void addRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
