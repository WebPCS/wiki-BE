package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.pah.comwiki.dto.user.LoginDto;
import kr.pah.comwiki.dto.user.RegisterDto;
import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 모든 사용자 조회
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 사용자 조회 (ID 기준)
    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable java.util.UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginDto loginDto,
                                            HttpSession session) {
        return userService.loginUser(loginDto, session);
    }

    @GetMapping("/checkLogin")
    public ResponseEntity<?> checkLogin(HttpSession session) {
        return ResponseEntity.ok(session.getAttribute("uid"));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(HttpSession session, @RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("student_number") String student_number) {

        Users user = userService.getUserById((UUID) session.getAttribute("userId"));
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setStudentNumber(student_number);
        userService.updateUser(user);
        return ResponseEntity.ok("User Update");
    }

    // 사용자 등록
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterDto registerDto, HttpSession session) {
        return userService.saveUser(registerDto);
    }
}
