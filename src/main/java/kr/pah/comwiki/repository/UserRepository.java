package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users findByUid(UUID id);
}
