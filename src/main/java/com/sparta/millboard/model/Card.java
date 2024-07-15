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

    @ManyToOne
    @JoinColumn(name = "column_id")
    private BoardColumn column;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToOne
    @JoinColumn(name = "assignee_id")
    private User assignee;

    public void setColumn(BoardColumn column) {
        this.column=column;
        column.setCard(this);
    }

    public void update(Card card) {
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.dueDate = card.getDueDate();
    }
}
