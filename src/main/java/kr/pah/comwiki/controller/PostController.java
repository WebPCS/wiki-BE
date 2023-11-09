package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.pah.comwiki.dto.post.CreatePostDto;
import kr.pah.comwiki.service.PostService;
import kr.pah.comwiki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/search/{title}")
    public ResponseEntity<?> getPostsByTitle(@Valid @PathVariable String title) {
        return postService.getPostByTitle(title);
    }

    @PostMapping("/")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto createPostDto, HttpSession session) {
        if (session.getAttribute("uid") == null || session.getAttribute("uid").toString().isBlank()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "login.html");
            return new ResponseEntity<>(headers, HttpStatus.PERMANENT_REDIRECT);
        }
        return postService.createPost(createPostDto, session);
    }


}