package kr.pah.comwiki.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class History {
    @Id
    @GeneratedValue
    private java.util.UUID id;
    private String content;
    private LocalDateTime editedAt;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Users editor;

}
