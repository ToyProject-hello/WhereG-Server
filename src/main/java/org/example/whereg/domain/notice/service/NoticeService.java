package org.example.whereg.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.notice.dto.request.CreateNoticeRequest;
import org.example.whereg.domain.notice.dto.request.UpdateNoticeRequest;
import org.example.whereg.domain.notice.dto.response.NoticeResponse;
import org.example.whereg.domain.notice.entity.Notice;
import org.example.whereg.domain.notice.repository.NoticeRepository;
import org.example.whereg.domain.user.entity.User;
import org.example.whereg.global.exception.GlobalException;
import org.example.whereg.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional
    public void createNotice(User user, CreateNoticeRequest request) {
        Notice notice = Notice.create(user, request.title(), request.content());
        noticeRepository.save(notice);
    }

    @Transactional(readOnly = true)
    public Page<NoticeResponse> getAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable)
                .map(NoticeResponse::from);
    }

    @Transactional
    public void updateNotice(Long noticeId, UpdateNoticeRequest request) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOTICE_NOT_FOUND));
        notice.update(request.title(), request.content());
    }

    @Transactional
    public void deleteNotice(Long noticeId) {
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
}
