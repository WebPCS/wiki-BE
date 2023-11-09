package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findPostById(Long id);
    Post findPostByTitle(String title);
}
