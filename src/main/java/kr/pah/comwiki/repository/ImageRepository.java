package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
