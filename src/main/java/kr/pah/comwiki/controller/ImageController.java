package kr.pah.comwiki.controller;

import jakarta.validation.Valid;
import kr.pah.comwiki.entity.Image;
import kr.pah.comwiki.service.ImageService;
import kr.pah.comwiki.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor

public class ImageController {
    private final ImageService imageService;

    // 이미지 업로드
    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Image savedImage = imageService.saveImage(file);
        return Result.create(HttpStatus.OK, "업로드 성공");
    }

    // 이미지 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@Valid @RequestBody UUID id) {
        Image image = imageService.getImageById(id);
        return Result.create(HttpStatus.OK, "완료");
    }
}