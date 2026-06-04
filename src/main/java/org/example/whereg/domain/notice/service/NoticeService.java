package org.example.whereg.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.notice.dto.request.CreateNoticeRequest;
import org.example.whereg.domain.notice.dto.request.UpdateNoticeRequest;
import org.example.whereg.domain.notice.dto.response.NoticeResponse;
import org.example.whereg.domain.notice.entity.Notice;
import org.example.whereg.domain.notice.repository.NoticeRepository;
import org.example.whereg.domain.user.entity.User;
import org.example.whereg.domain.user.enums.Role;
import org.example.whereg.global.exception.GlobalException;
import org.example.whereg.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void createNotice(User user, CreateNoticeRequest request) {
        validateAdmin(user);
        Notice notice = Notice.create(user, request.title(), request.content());
        noticeRepository.save(notice);
    }

    @Transactional(readOnly = true)
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(NoticeResponse::from)
                .toList();
    }

    @Transactional
    public void updateNotice(User user, UpdateNoticeRequest request) {
        validateAdmin(user);
        Notice notice = noticeRepository.findById(request.noticeId())
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTICE_NOT_FOUND));
        notice.update(request.title(), request.content());
    }

    @Transactional
    public void deleteNotice(User user, Long noticeId) {
        validateAdmin(user);
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTICE_NOT_FOUND));
        noticeRepository.delete(notice);
    }

    @Transactional(readOnly = true)
    public NoticeResponse getNotice(Long noticeId) {
        Notice notice = noticeRepository.findWithAuthorById(noticeId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTICE_NOT_FOUND));
        return NoticeResponse.from(notice);
    }

    private void validateAdmin(User user) {
        if (user == null || user.getRole() != Role.ADMIN) {
            throw new GlobalException(ErrorCode.FORBIDDEN);
        }
    }
}