package com.sparta.millboard.model;

import jakarta.persistence.*;
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
        this.column=column;
        column.setCard(this);
    }

    public void setAuthor(User user) {
        this.author = user;
    }

    public void setWorker(User user){
        this.worker = user;
    }

    public void update(Card card) {
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.dueDate = card.getDueDate();
    }
}
