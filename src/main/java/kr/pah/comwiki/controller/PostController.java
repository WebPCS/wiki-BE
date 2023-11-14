package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import kr.pah.comwiki.dto.post.CreatePostDto;
import kr.pah.comwiki.dto.post.UpdatePostDto;
import kr.pah.comwiki.service.PostService;
import kr.pah.comwiki.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 제목으로 검색
    @GetMapping("/search/{title}")
    public ResponseEntity<?> getPostsByTitle(@Valid @PathVariable String title) {
        return postService.getPostByTitle(title);
    }

    // 게시글 작성
    @PostMapping("/")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostDto createPostDto, HttpSession session) {
        if (session.getAttribute("uid") == null || session.getAttribute("uid").toString().isBlank()) {
            return Result.login();
        }
        return postService.createPost(createPostDto, session);
    }

    // 게시글 업데이트
    @PostMapping("/updatePost")
    public ResponseEntity<?> updatePost(@Valid @RequestBody UpdatePostDto updatePostDto, HttpSession session) {
        if (session.getAttribute("uid") == null || session.getAttribute("uid").toString().isBlank()) {
            return Result.login();
        }
        return postService.updatePost(updatePostDto, session);
    }
}