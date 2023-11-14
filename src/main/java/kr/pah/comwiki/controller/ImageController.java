package kr.pah.comwiki.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        return Result.create(HttpStatus.OK, imageService.saveImage(file, session));
    }

    // 이미지 조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@Valid @PathVariable UUID id) throws IOException {
        return imageService.getImageById(id);
    }
}