package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.pah.comwiki.dto.user.LoginDto;
import kr.pah.comwiki.dto.user.RegisterDto;
import kr.pah.comwiki.dto.user.UpdateDto;
import kr.pah.comwiki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDto loginDto,
                                       HttpSession session) {
        return userService.loginUser(loginDto, session);
    }

    // 사용자 업데이트
    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateDto updateDto, HttpSession session) {
        return userService.updateUser(updateDto, session);
    }

    // 사용자 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterDto registerDto, HttpSession session) {
        return userService.saveUser(registerDto);
    }
}
