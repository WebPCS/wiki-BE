package kr.pah.comwiki.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private UUID uid; // 고유번호
    private String name; // 이름
    private String username; // 아이디
    private String email; // 이메일
    private String password; // 비밀번호
    private String studentNumber; // 학번

    @OneToMany(mappedBy = "author", cascade = ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "editor", cascade = ALL)
    private List<History> editHistory = new ArrayList<>();

    // 새로운 엔티티가 생성될 때 UUID 생성
    @PrePersist
    public void autofill() {
        if (this.uid == null) {
            this.uid = java.util.UUID.randomUUID();
        }
    }

    public java.util.UUID getUid() {
        return uid;
    }

    public void addEditHistory(History history) {
        editHistory.add(history);
        history.setEditor(this);
    }

    public Users(String name, String userId, String email, String password, String studentNumber) {
        this.name = name;
        this.username = userId;
        this.email = email;
        this.password = password;
        this.studentNumber = studentNumber;
    }
}
