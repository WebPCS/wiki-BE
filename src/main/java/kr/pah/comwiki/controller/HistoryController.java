package kr.pah.comwiki.controller;

import kr.pah.comwiki.entity.History;
import kr.pah.comwiki.service.HistoryService;
import kr.pah.comwiki.service.PostService;
import kr.pah.comwiki.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;
    private final UserService userService;
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<History>> getAllHistories() {
        return ResponseEntity.ok(historyService.getAllHistories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable UUID id) {
        History history = historyService.getHistoryById(id);
        if (history == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(history);
    }

//    @PostMapping
//    public ResponseEntity<History> createHistory(@RequestBody HistoryDto.WriteHistoryDto historyDto, HttpSession session) {
//        Users user = userService.getUserById((UUID)session.getAttribute("uid"));
//        Post relatedPost = postService.getPostById(historyDto.getPostId());
//        History history = new History(historyDto.getContent(), relatedPost, user);
//        return ResponseEntity.ok(historyService.saveHistory(history, user));
//    }
}
