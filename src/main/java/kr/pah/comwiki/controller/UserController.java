package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.repository.UserRepository;
import kr.pah.comwiki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

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
    public ResponseEntity<Users> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("userId") String userId,
                            @RequestParam("password") String password,
                            HttpSession session,
                            Model model) {
        Users user = userRepository.findByUserId(userId);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("USER", user); // 세션에 사용자 정보 저장
            return "ok"; // 로그인 성공시 리다이렉트할 페이지
        } else {
            model.addAttribute("error", "ID or Password Wrong.");
            return "fail"; // 로그인 페이지로 다시 리다이렉트
        }
    }

    // 사용자 등록
    @PostMapping("/register")
    public ResponseEntity<Users> registerUser(@RequestParam("profileImage") MultipartFile file,
                                              @RequestParam("name") String name,
                                              @RequestParam("userId") String userId,
                                              @RequestParam("email") String email,
                                              @RequestParam("password") String password,
                                              @RequestParam("studentNumber") String studentNumber) {
        try {
            byte[] profileImage = file.getBytes();
            Users user = new Users();
            user.setName(name);
            user.setUserId(userId);
            user.setEmail(email);
            user.setPassword(password);
            user.setStudentNumber(studentNumber);
            user.setProfileImage(profileImage);

            Users registeredUser = userService.registerUser(user);

            return ResponseEntity.ok(registeredUser);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 사용자 정보 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable UUID id, @RequestBody Users user) {
        if (!userService.getUserById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id); // 요청된 ID로 설정
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
