// CreateCommentRequest.java
package org.example.whereg.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCommentRequest(
        @NotBlank(message = "내용은 필수입니다.")
        String content,
        Long parentId  // 답글이면 부모 댓글 ID, 일반 댓글이면 null
) {}