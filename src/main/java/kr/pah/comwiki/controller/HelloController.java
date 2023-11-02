package kr.pah.comwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/post")
    public String writePost() {
        return "write_post.html";
    }

    @GetMapping("/history")
    public String history() {
        return "history.html";
    }
}
