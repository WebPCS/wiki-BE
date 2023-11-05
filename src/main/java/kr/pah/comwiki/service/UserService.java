package kr.pah.comwiki.service;

import jakarta.servlet.http.HttpSession;
import kr.pah.comwiki.dto.user.LoginDto;
import kr.pah.comwiki.dto.user.RegisterDto;
import kr.pah.comwiki.entity.Users;
import kr.pah.comwiki.repository.UserRepository;
import kr.pah.comwiki.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(UUID id) {
        return userRepository.findByUid(id);
    }

    @Transactional
    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    public ResponseEntity<?> loginUser(LoginDto loginDto, HttpSession session) {
        Users user = userRepository.findByUsername(loginDto.getUsername());
        if (user != null && user.getPassword().equals(loginDto.getPassword())) {
            session.setAttribute("uid", user.getUid());
            return new Result<>().create(200, "로그인 성공 : " + user.getUsername());
        }
        return new Result<>().create(200, "아이디 혹은 비밀번호가 잘못 되었습니다.");
    }

    @Transactional
    public ResponseEntity<?> saveUser(RegisterDto registerDto) {
        Users checkDuplicateId = userRepository.findByUsername(registerDto.getUsername());
        if (checkDuplicateId != null) {
            return new Result<>().create(200, "중복된 아이디");
        }
        Users user = new Users(registerDto.getName(), registerDto.getUsername(), registerDto.getEmail(), registerDto.getPassword(), registerDto.getStudentNumber());
        userRepository.save(user);
        return new Result<>().create(200, "정상적으로 회원가입 되었습니다.");
    }
}