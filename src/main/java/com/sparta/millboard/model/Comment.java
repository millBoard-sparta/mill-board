package com.sparta.millboard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
@SuperBuilder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    public void update(Comment entity) {
        if (entity.getContent() != null && !entity.getContent().isEmpty()) {
            this.content = entity.getContent();
        }
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
