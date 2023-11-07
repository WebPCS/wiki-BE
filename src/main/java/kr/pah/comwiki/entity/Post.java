package kr.pah.comwiki.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<History> histories = new ArrayList<>();

    @PrePersist
    private void saved() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void updated() {
        updatedAt = LocalDateTime.now();
    }

    public Post(String title, String content, Users author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
