package kr.pah.comwiki.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
public class History {
    @Id
    @GeneratedValue
    private UUID id;
    private String content;
    private LocalDateTime editedAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Users editor;

    public History() {

    }

    @PreUpdate
    private void saved() {
        editedAt = LocalDateTime.now();
    }

    public History(String content, Post post, Users editor) {
        this.content = content;
        this.post = post;
        this.editor = editor;
    }
}
