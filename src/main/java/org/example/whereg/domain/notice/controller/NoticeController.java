package org.example.whereg.domain.notice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.notice.dto.request.CreateNoticeRequest;
import org.example.whereg.domain.notice.dto.request.UpdateNoticeRequest;
import org.example.whereg.domain.notice.dto.response.NoticeResponse;
import org.example.whereg.domain.notice.service.NoticeService;
import org.example.whereg.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createNotice(
            @RequestBody @Valid CreateNoticeRequest request,
            @AuthenticationPrincipal User user) {
        noticeService.createNotice(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<Page<NoticeResponse>> getAllNotices(Pageable pageable) {
        return ResponseEntity.ok(noticeService.getAllNotices(pageable));
    }

    @PatchMapping("/{noticeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody @Valid UpdateNoticeRequest request) {
        noticeService.updateNotice(noticeId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{noticeId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponse> getNotice(@PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.getNotice(noticeId));
    }
}
