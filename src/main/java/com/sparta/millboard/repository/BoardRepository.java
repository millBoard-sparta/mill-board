package com.sparta.millboard.repository;

import com.sparta.millboard.model.Board;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("""
            select distinct b from Board b
            left join fetch b.boardColumns bc
            left join fetch bc.cardList
            where b.id = :boardId
            order by bc.order
            """)
    Optional<Board> findByIdWithColumnsAndCards(@Param("boardId") Long boardId);
}
