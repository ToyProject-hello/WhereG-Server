package org.example.whereg.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.whereg.domain.post.entity.ContentType;

public record CreatePostRequest(
        @NotBlank String title,
        @NotNull ContentType contentType,
        String foundPlace,
        String photoUrl,
        String content
) {}