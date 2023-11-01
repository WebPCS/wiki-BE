package kr.pah.comwiki.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
public class Users {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private UUID id;
    private String name;
    private String userId;
    private String email;
    private String password;
    private String studentNumber;

    @OneToMany(mappedBy = "author", cascade = ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "editor", cascade = ALL)
    private List<History> editHistory = new ArrayList<>();

    // 새로운 엔티티가 생성될 때 UUID 생성
    @PrePersist
    public void autofill() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return id;
    }

    public void addEditHistory(History history) {
        editHistory.add(history);
        history.setEditor(this);
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<History> getEditHistory() {
        return editHistory;
    }

    public void setEditHistory(List<History> editHistory) {
        this.editHistory = editHistory;
    }
}
