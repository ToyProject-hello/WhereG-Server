// CommentResponse.java
package org.example.whereg.domain.comment.dto.response;

import org.example.whereg.domain.comment.entity.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponse(
        Long id,
        String authorName,
        String content,
        LocalDateTime createdAt,
        List<CommentResponse> replies
) {
    public static CommentResponse from(Comment comment, List<CommentResponse> replies) {
        return new CommentResponse(
                comment.getId(),
                comment.getAuthor().getName(),
                comment.getContent(),
                comment.getCreatedAt(),
                replies
        );
    }
}