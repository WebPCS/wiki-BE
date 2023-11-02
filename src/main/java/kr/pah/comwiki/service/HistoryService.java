package kr.pah.comwiki.service;

import kr.pah.comwiki.entity.History;
import kr.pah.comwiki.entity.Post;
import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.repository.HistoryRepository;
import kr.pah.comwiki.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final PostService postService;

    @Transactional(readOnly = true)
    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public History getHistoryById(UUID id) {
        return historyRepository.findById(id).orElse(null);
    }

    @Transactional
    public History saveHistory(History history, Users user) {
        history.setEditor(user);
        postService.updatePostContent(history.getPost().getId(), history.getContent());
        return historyRepository.save(history);
    }

}
