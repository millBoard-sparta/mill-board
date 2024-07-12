package com.sparta.millboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/board")
    public String board() {
        return "board";
    }

    @GetMapping("/board/new")
    public String newBoard() {
        return "board_form";
    }

    @GetMapping("/board/edit")
    public String editBoard() {
        return "board_form";
    }

    @GetMapping("/card/detail")
    public String cardDetail() {
        return "card_detail";
    }
}
