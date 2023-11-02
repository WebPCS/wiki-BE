package kr.pah.comwiki.service;

import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Users getUserById(java.util.UUID id) {
        return userRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Users getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }
}