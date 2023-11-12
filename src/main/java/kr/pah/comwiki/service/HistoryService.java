package kr.pah.comwiki.service;

import kr.pah.comwiki.entity.History;
import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.repository.HistoryRepository;
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

    @Transactional(readOnly = true)
    public List<History> getAllHistories() {
        return historyRepository.findAll();
    }

    @Transactional(readOnly = true)
    public History getHistoryById(UUID id) {
        return historyRepository.findById(id).orElse(null);
    }

}