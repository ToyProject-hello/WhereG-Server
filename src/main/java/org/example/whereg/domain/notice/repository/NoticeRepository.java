package org.example.whereg.domain.notice.repository;

import org.example.whereg.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @EntityGraph(attributePaths = "author")
    List<Notice> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = "author")
    Optional<Notice> findWithAuthorById(Long id);
}