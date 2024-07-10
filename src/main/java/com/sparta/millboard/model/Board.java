package com.sparta.millboard.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "boards")
@SuperBuilder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardColumn> boardColumns;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardPartner> boardPartners;

    public void update(Board entity) {
        if (entity.getTitle() != null && !entity.getTitle().isEmpty()) {
            this.title = entity.getTitle();
        }
        if (entity.getDescription() != null && !entity.getDescription().isEmpty()) {
            this.description = entity.getDescription();
        }
    }
}
