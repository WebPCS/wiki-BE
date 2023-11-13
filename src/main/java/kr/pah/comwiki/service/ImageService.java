package kr.pah.comwiki.service;

import kr.pah.comwiki.entity.Image;
import kr.pah.comwiki.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 이미지 저장
    @Transactional
    public Image saveImage(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        Path storageDirectory = Paths.get(uploadDir);
        if (!Files.exists(storageDirectory)) {
            Files.createDirectories(storageDirectory);
        }
        Path destinationPath = storageDirectory.resolve(Paths.get(filename))
                .normalize().toAbsolutePath();
        try {
            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IOException("파일 저장에 실패했습니다: " + filename, e);
        }

        Image image = new Image();
        image.setFilename(filename);
        return imageRepository.save(image);
    }

    public Image getImageById(UUID id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException("이미지를 찾을 수 없습니다."));
    }
}