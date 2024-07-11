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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardPartnerRepository boardPartnerRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardCreateDto boardCreateDto) {
        return BoardResponseDto.from(
                this.boardRepository.save(boardCreateDto.toEntity())
        );
    }

    public Board getBoardById(Long id) {
        return this.boardRepository.getReferenceById(id);
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
}
