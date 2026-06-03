package org.example.whereg.domain.post.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.whereg.domain.post.entity.ContentType;

public record CreatePostRequest(
        @NotBlank @Size(max = 255) String title,
        @NotNull ContentType contentType,
        @Size(max = 255) String foundPlace,
        @Size(max = 1000) String photoUrl,
        String content
) {}