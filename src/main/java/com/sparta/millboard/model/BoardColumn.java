package com.sparta.millboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
@Entity
@Table(name = "columns")
public class BoardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "column_id")
    private Long id;

    private String statusName;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "column_order")
    private Integer order;

    public void setBoard(Board board) {
        this.board = board;
    }

    public void update(BoardColumn column) {
        if (column.getStatusName() != null && !column.getStatusName().isBlank()) {
            this.statusName = column.getStatusName();
        }
        if (column.getOrder() != null && column.getOrder() != 0) {
            this.order = column.getOrder();
        }
    }
}
