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
    public String saveImage(MultipartFile file, HttpSession session) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String storageFilename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

        Path destinationPath = Paths.get(uploadDir).resolve(storageFilename).normalize().toAbsolutePath();
        Files.createDirectories(destinationPath.getParent());
        Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

        Image image = new Image();
        image.setId(UUID.fromString(storageFilename.split("\\.")[0]));
        image.setExtension(storageFilename.split("\\.")[1]);
        image.setUploader(userRepository.findByUid((UUID) session.getAttribute("uid")));
        image.setFilename(originalFilename);
        imageRepository.save(image);

        return storageFilename.split("\\.")[0];
    }


    public ResponseEntity<?> getImageById(UUID id) throws IOException {
        String filename = imageRepository.findImageById(id).getId() + "." + imageRepository.findImageById(id).getExtension();
        FileSystemResource resource = new FileSystemResource(uploadDir + filename);
        if (!resource.exists()) {
            return Result.create(HttpStatus.NOT_FOUND, "파일이 존재하지 않습니다.");
        }
        HttpHeaders headers = new HttpHeaders();
        Path file = Paths.get(uploadDir + filename);
        headers.add("Content-Type", Files.probeContentType(file));
        return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
    }
}