package org.example.whereg.domain.post.dto.response;

import org.example.whereg.domain.post.entity.ContentType;
import org.example.whereg.domain.post.entity.Post;
import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        String title,
        ContentType contentType,
        String foundPlace,
        String photoUrl,
        String content,
        String authorName,
        LocalDateTime createdAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContentType(),
                post.getFoundPlace(),
                post.getPhotoUrl(),
                post.getContent(),
                post.getAuthor().getName(),
                post.getCreatedAt()
        );
    }
}