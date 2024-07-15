package com.sparta.millboard.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private String authorName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private BoardColumn column;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private User worker;

    public void setAuthor(String author) {
        this.authorName = author;
    }

    public void setColumn(BoardColumn column) {
        this.column = column;
        column.setCard(this);
    }

    public void updateColumn(BoardColumn column) {
        if (this.column == null || Objects.equals(this.column.getId(), column.getId())) {
            return;
        }
        this.column = column;
        column.setCard(this);
    }

    public void setWorker(User user) {
        this.worker = user;
        user.updateCard(this);
    }

    public void update(Card card) {
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.dueDate = card.getDueDate();
    }
}
