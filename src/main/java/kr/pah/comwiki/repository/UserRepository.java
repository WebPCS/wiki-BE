package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUserId(String userId);
    Optional<Users> findById(UUID id);
}
