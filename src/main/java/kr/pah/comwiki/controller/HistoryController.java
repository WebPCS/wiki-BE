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

    // 히스토리 ID으로 히스토리 조회
    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable UUID id) {
        History history = historyService.getHistoryById(id);
        if (history == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(history);
    }
}
