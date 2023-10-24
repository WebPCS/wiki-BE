package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
