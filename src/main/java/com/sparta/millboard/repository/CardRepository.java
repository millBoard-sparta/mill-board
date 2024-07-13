package com.sparta.millboard.repository;

import com.sparta.millboard.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends JpaRepository<Card, Long> {

    Page<Card> findByColumnId(Long columnId, Pageable pageable);

    Page<Card> findByAuthorId(Long userId, Pageable pageable);


    Page<Card> findByColumnIdAndAuthorId(@Param("columnId") Long columnId,
        @Param("userId") Long userId, Pageable pageable);
}
