package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.BoardCreateDto;
import com.sparta.millboard.dto.request.BoardUpdateDto;
import com.sparta.millboard.dto.response.BoardPartnerResponseDto;
import com.sparta.millboard.dto.response.BoardResponseDto;
import com.sparta.millboard.model.Board;
import com.sparta.millboard.model.BoardPartner;
import com.sparta.millboard.model.User;
import com.sparta.millboard.repository.BoardPartnerRepository;
import com.sparta.millboard.repository.BoardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardPartnerRepository boardPartnerRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardCreateDto boardCreateDto) {
        return BoardResponseDto.from(
                boardRepository.save(boardCreateDto.toEntity())
        );
    }

    public Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not exist"));

        board.update(boardUpdateDto.toEntity());

        return BoardResponseDto.from(board);
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardPartnerResponseDto addPartner(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow();
        BoardPartner partner = BoardPartner.builder()
                .user(user)
                .board(board)
                .build();
        return BoardPartnerResponseDto.from(this.boardPartnerRepository.save(partner));
    }

    @Transactional
    public void deletePartner(Long boardId, Long partnerId) {
        boardPartnerRepository.deleteById(partnerId);
    }

    @Transactional
    public BoardResponseDto getBoardWithColumnsAndCards(Long boardId) {
        Board boardWithColumns = boardRepository.findByIdWithColumnsAndCards(boardId)
                .orElseThrow(() -> new RuntimeException("not found board"));
        return BoardResponseDto.from(boardWithColumns, true);
    }
}
