package kr.pah.comwiki.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/register")
    public String register() {
        return "register.html";
    }
}
