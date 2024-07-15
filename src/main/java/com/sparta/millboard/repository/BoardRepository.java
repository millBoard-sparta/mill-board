package com.sparta.millboard.repository;

import com.sparta.millboard.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // TODO: board with columns
    @Query("select b from Board b join fetch BoardColumn bc on bc.board.id = b.id where b.id = :boardId")
    Optional<Board> findByIdWithColumns(@Param("boardId") Long boardId);
}
