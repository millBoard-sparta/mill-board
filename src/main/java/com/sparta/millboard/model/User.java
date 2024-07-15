package com.sparta.millboard.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @OneToMany(mappedBy = "worker", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cardSet;

    public void updateUsername(String newUsername) {
        this.username = newUsername;
    }

    public enum Role {
        USER,
        MANAGER,
    }

    public enum status {
        INACTIVE,
        ACTIVE,
        DISABLED
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

    public void updatePassword(String password) {
        this.password = password;
    }

    public void removeCard(Card card) {
        this.cardSet.remove(card);
    }

    public void updateCard(Card card) {
        this.cardSet.add(card);
    }

}
