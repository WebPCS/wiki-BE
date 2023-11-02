package kr.pah.comwiki.service;

import kr.pah.comwiki.entity.History;
import kr.pah.comwiki.entity.Post;
import kr.pah.comwiki.exception.ResourceNotFoundException;
import kr.pah.comwiki.repository.HistoryRepository;
import kr.pah.comwiki.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private HistoryRepository historyRepository;

    public ResponseEntity<String> createPost(Post post) {
        postRepository.save(post);
        return ResponseEntity.ok("정상적으로 게시글이 작성되었다");
    }

    public Post updatePost(Long id, Post postDetails) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        post.setTitle(postDetails.getTitle());
        post.setContent(postDetails.getContent());
        post.setUpdatedAt(LocalDateTime.now());

        History history = new History();
        history.setContent(postDetails.getContent());
        history.setEditedAt(LocalDateTime.now());
        history.setPost(post);
        historyRepository.save(history);

        return postRepository.save(post);
    }

    public List<Post> findPostsByTitle(String title) {
        return postRepository.findByTitleContaining(title);
    }

}
