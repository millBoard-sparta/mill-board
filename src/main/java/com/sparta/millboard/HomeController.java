package com.sparta.millboard;

import com.sparta.millboard.dto.request.BoardCreateDto;
import com.sparta.millboard.dto.request.UserRequestDto;
import com.sparta.millboard.model.Board;
import com.sparta.millboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/auth")
    public String auth(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "auth";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/board/new")
    public String newBoardForm(Model model) {
        model.addAttribute("boardCreateDto", new BoardCreateDto());
        return "board_form";
    }

    @GetMapping("/board/{boardId}")
    public String board(@PathVariable Long boardId, Model model) {
        Board board = boardService.getBoardById(boardId);
        model.addAttribute("board", board);
        return "board";
    }

    @GetMapping("/my-boards")
    public String myBoards() {
        return "my-boards";
    }
}
