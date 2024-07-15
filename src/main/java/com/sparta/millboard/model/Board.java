package com.sparta.millboard.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String description;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BoardColumn> boardColumns;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardPartner> boardPartners;

    @Builder
    public Board(String title, String description, Set<BoardColumn> boardColumns, List<BoardPartner> boardPartners) {
        this.title = title;
        this.description = description;
        this.boardColumns = boardColumns;
        this.boardPartners = boardPartners;
    }

    public void update(Board entity) {
        if (entity.getTitle() != null && !entity.getTitle().isEmpty()) {
            this.title = entity.getTitle();
        }
        if (entity.getDescription() != null && !entity.getDescription().isEmpty()) {
            this.description = entity.getDescription();
        }
    }
}
