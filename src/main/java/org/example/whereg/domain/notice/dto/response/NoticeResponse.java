package org.example.whereg.domain.notice.dto.response;

import org.example.whereg.domain.notice.entity.Notice;

import java.time.LocalDateTime;

public record NoticeResponse(
        Long id,
        String title,
        String content,
        String authorName,
        LocalDateTime createdAt
) {
    public static NoticeResponse from(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getAuthor().getName(),
                notice.getCreatedAt()
        );
    }
}