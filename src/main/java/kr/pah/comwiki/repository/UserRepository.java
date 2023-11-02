package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByUserId(String userId);
    Users findById(java.util.UUID id);
}
