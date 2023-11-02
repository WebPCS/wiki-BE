package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HistoryRepository extends JpaRepository<History, UUID> {
}
