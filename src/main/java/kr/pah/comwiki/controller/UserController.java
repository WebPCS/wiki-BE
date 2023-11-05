package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public String loginUser(@RequestParam("userId") String userId,
                            @RequestParam("password") String password,
                            HttpSession session,
                            Model model) {
        Users user = userService.getUserByUserId(userId);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("userId", user.getId()); // 세션에 사용자 정보 저장
            return "ok" + session.getAttribute("userId").toString(); // 로그인 성공시 리다이렉트할 페이지
        } else {
            model.addAttribute("error", "ID or Password Wrong.");
            return "fail"; // 로그인 페이지로 다시 리다이렉트
        }
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
    public ResponseEntity<Users> registerUser(Users user) {
        Users registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
}
