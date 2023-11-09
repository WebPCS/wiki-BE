package kr.pah.comwiki.service;

import jakarta.servlet.http.HttpSession;
import kr.pah.comwiki.dto.post.CreatePostDto;
import kr.pah.comwiki.entity.History;
import kr.pah.comwiki.entity.Post;
import kr.pah.comwiki.exception.ResourceNotFoundException;
import kr.pah.comwiki.repository.HistoryRepository;
import kr.pah.comwiki.repository.PostRepository;
import kr.pah.comwiki.repository.UserRepository;
import kr.pah.comwiki.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final HistoryRepository historyRepository;
    private final UserRepository userRepository;

    @Transactional()
    public ResponseEntity<?> createPost(CreatePostDto createPostDto, HttpSession session) {
        Post post = new Post(createPostDto.getTitle(), createPostDto.getContent(), userRepository.findByUid((UUID) session.getAttribute("uid")));
        postRepository.save(post);
        History history = new History();
        history.setEditor(post.getAuthor());
        history.setPost(post);
        history.setContent(post.getContent());
        historyRepository.save(history);
        return Result.create(200, "정상적으로 게시글이 작성되었습니다.");
    }

    public ResponseEntity<?> getPostByTitle(String title) {
        Post post = postRepository.findPostByTitle(title);
        if (post != null) {
            return ResponseEntity.ok(post);
        }
        return Result.create(404, "일치하는 게시글을 찾을 수 없습니다.");
    }

    @Transactional
    public void updatePostContent(Long postId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post 404 " + postId));
        post.setContent(content);
        postRepository.save(post);
    }
}
