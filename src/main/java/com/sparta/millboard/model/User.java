package com.sparta.millboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    private String refreshToken; // uuid

    private Role role;

    public enum Role {
        USER,
        MANAGER
    }
}
