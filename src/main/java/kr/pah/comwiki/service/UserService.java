package kr.pah.comwiki.service;

import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Users registerUser(Users user) {
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Users> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Users> getUserByUserId(String userId) {
        return Optional.ofNullable(userRepository.findByUserId(userId));
    }

    @Transactional
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }
}