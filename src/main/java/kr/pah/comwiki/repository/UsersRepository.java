package kr.pah.comwiki.repository;

import kr.pah.comwiki.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
