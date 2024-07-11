package com.sparta.millboard.service;

import com.sparta.millboard.dto.request.BoardColumnCreateDto;
import com.sparta.millboard.dto.request.BoardColumnUpdateDto;
import com.sparta.millboard.dto.response.BoardColumnResponseDto;
import com.sparta.millboard.dto.response.BoardResponseDto;
import com.sparta.millboard.model.Board;
import com.sparta.millboard.model.BoardColumn;
import com.sparta.millboard.repository.BoardColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardColumnService {
    private final BoardService boardService;
    private final BoardColumnRepository boardColumnRepository;

    public BoardColumn getById(Long id) {
        return boardColumnRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("not found"));
    }

    @Transactional
    public BoardColumnResponseDto createBoardColumn(BoardColumnCreateDto requestDto, Long boardId) {
        Board board = boardService.getBoardById(boardId);
        BoardColumn bc = boardColumnRepository.save(requestDto.toEntity());
        bc.setBoard(board);

        return BoardColumnResponseDto.from(bc);
    }

    @Transactional
    public BoardColumnResponseDto updateBoardColumn(BoardColumnUpdateDto requestDto, Long columnId) {
        BoardColumn column = boardColumnRepository.findById(columnId).orElseThrow(() -> new RuntimeException("not found"));
        column.update(requestDto.toEntity());
        return BoardColumnResponseDto.from(column);
    }

    public void deleteById(Long columnId) {
        boardColumnRepository.deleteById(columnId);
    }

}
