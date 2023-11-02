package kr.pah.comwiki.service;

import jakarta.transaction.Transactional;
import kr.pah.comwiki.entity.History;
import kr.pah.comwiki.entity.Post;
import kr.pah.comwiki.exception.ResourceNotFoundException;
import kr.pah.comwiki.repository.HistoryRepository;
import kr.pah.comwiki.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final HistoryRepository historyRepository;

    public ResponseEntity<String> createPost(Post post) {
        postRepository.save(post);
        History history = new History();
        history.setEditor(post.getAuthor());
        history.setPost(post);
        history.setContent(post.getContent());
        historyRepository.save(history);
        return ResponseEntity.ok("정상적으로 게시글이 작성되었다");
    }

    public List<Post> findPostsByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post 404 : " + postId));
    }

    @Transactional
    public void updatePostContent(Long postId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post 404 " + postId));
        post.setContent(content);
        postRepository.save(post);
    }
}
