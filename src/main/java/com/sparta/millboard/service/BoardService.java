package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.BoardCreateDto;
import com.sparta.millboard.dto.response.BoardResponseDto;
import com.sparta.millboard.model.Board;
import com.sparta.millboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardCreateDto boardCreateDto) {
        Board board = this.boardRepository.save(boardCreateDto.toEntity());
        return BoardResponseDto.from(board);
    }
}
