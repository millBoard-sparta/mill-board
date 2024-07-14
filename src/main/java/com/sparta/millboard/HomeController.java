package com.sparta.millboard;

import com.sparta.millboard.dto.request.UserRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/auth")
    public String auth(Model model) {
        model.addAttribute("userRequestDto", new UserRequestDto());
        return "auth";
    }

    @GetMapping("/board")
    public String getBoardPage() {
        return "board";
    }
}
