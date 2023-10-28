package kr.pah.comwiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import kr.pah.comwiki.entity.Post;
import kr.pah.comwiki.repository.PostRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class PostController {

    private final PostRepository postRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            // 파일 이름 생성 (실제 상황에 맞게 조정할 수 있음)
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = System.currentTimeMillis() + fileExtension;

            // 파일 저장 경로 설정
            Path copyLocation = Paths.get(uploadDir + File.separator + newFilename);
            file.transferTo(copyLocation); // 파일 저장

            Post post = new Post();
            post.setImagePath(copyLocation.toString()); // 이미지 경로 설정
            postRepository.save(post); // 데이터베이스에 게시글 정보 저장

            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
