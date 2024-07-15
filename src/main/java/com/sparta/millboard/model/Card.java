package com.sparta.millboard.model;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "cards")
@NoArgsConstructor
@Getter
@SuperBuilder
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long id;

    private String title;
    private String description;
    private String dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private BoardColumn column;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private User worker;

    public void setColumn(BoardColumn column) {
        column.setCard(this);
    }

    public void updateColumn(BoardColumn column) {
        if (this.column == null || Objects.equals(this.column.getId(), column.getId())) {
            return;
        }
        this.column.removeCard(this);
        column.setCard(this);
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public void setWorker(User user){
        if (this.worker != null && !Objects.equals(this.worker.getId(), user.getId())) {
            this.worker = user;
        }
    }

    public void update(Card card) {
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.dueDate = card.getDueDate();
    }
}
