package kr.pah.comwiki.controller;

import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.service.UserService;
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
    public ResponseEntity<Users> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 사용자 등록
    @PostMapping
    public ResponseEntity<Users> registerUser(@RequestBody Users user) {
        Users newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
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
