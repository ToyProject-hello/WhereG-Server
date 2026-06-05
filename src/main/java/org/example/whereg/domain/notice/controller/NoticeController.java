package org.example.whereg.domain.notice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.notice.dto.request.CreateNoticeRequest;
import org.example.whereg.domain.notice.dto.request.UpdateNoticeRequest;
import org.example.whereg.domain.notice.dto.response.NoticeResponse;
import org.example.whereg.domain.notice.service.NoticeService;
import org.example.whereg.domain.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @PostMapping
    public ResponseEntity<Void> createNotice(
            @RequestBody @Valid CreateNoticeRequest request,
            @AuthenticationPrincipal User user) {
        noticeService.createNotice(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<NoticeResponse>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<Void> updateNotice(
            @PathVariable Long noticeId,
            @RequestBody @Valid UpdateNoticeRequest request,
            @AuthenticationPrincipal User user) {
        noticeService.updateNotice(user, noticeId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<Void> deleteNotice(
            @PathVariable Long noticeId,
            @AuthenticationPrincipal User user) {
        noticeService.deleteNotice(user, noticeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponse> getNotice(@PathVariable Long noticeId) {
        return ResponseEntity.ok(noticeService.getNotice(noticeId));
    }
}