package kr.pah.comwiki.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter @Setter
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
            this.id = java.util.UUID.randomUUID();
        }
    }

    public java.util.UUID getId() {
        return id;
    }

    public void addEditHistory(History history) {
        editHistory.add(history);
        history.setEditor(this);
    }
}
