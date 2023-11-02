package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import kr.pah.comwiki.dto.PostDto;
import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.service.PostService;
import kr.pah.comwiki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import kr.pah.comwiki.entity.Post;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/search")
    public ResponseEntity<List<Post>> getPostsByTitle(@RequestParam String title) {
        List<Post> posts = postService.findPostsByTitle(title);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/")
    public ResponseEntity<?> createPost(@RequestBody PostDto.WritePostDto writePostDto, HttpSession session) {
        if (session.getAttribute("userId") == null || session.getAttribute("userId").toString().isBlank()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "login.html");
            return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
        }
        Users user = userService.getUserById((UUID)session.getAttribute("userId"));
        return postService.createPost(new Post(writePostDto.getTitle(), writePostDto.getContent(), user));
    }
}