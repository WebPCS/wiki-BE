package kr.pah.comwiki.service;

import jakarta.servlet.http.HttpSession;
import kr.pah.comwiki.entity.Image;
import kr.pah.comwiki.repository.ImageRepository;
import kr.pah.comwiki.repository.UserRepository;
import kr.pah.comwiki.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 이미지 저장
    @Transactional
    public UUID saveImage(MultipartFile file, HttpSession session) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        String storageFilename = uuid + fileExtension;

        Path destinationPath = Paths.get(uploadDir, storageFilename).normalize().toAbsolutePath();
        Files.createDirectories(destinationPath.getParent());
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        Image image = new Image(uuid, originalFilename, fileExtension, userRepository.findByUid((UUID) session.getAttribute("uid")));
        imageRepository.save(image);

        return uuid;
    }

    // 이미지 불러오기
    public ResponseEntity<?> getImageById(UUID id) throws IOException {
        Image image = imageRepository.findImageById(id);
        if (image == null) {
            return Result.create(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다.");
        }

        String filename = image.getId() + "." + image.getExtension();
        Path filePath = Paths.get(uploadDir, filename);

        if (!Files.exists(filePath)) {
            return Result.create(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다.");
        }

        FileSystemResource resource = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", Files.probeContentType(filePath));
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}