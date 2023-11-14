package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    Image findImageById(UUID id);
}
