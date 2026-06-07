package org.example.whereg.domain.post.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.example.whereg.domain.user.entity.User;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentType contentType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String foundPlace;

    @Column(length = 1000)
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public static Post create(User author, ContentType contentType, String title,
                              String foundPlace, String photoUrl, String content) {
        Post post = new Post();
        post.author = author;
        post.contentType = contentType;
        post.title = title;
        post.foundPlace = foundPlace;
        post.photoUrl = photoUrl;
        post.content = content;
        return post;
    }
}
