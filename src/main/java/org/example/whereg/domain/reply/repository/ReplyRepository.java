package org.example.whereg.domain.reply.repository;

import org.example.whereg.domain.comment.entity.Comment;
import org.example.whereg.domain.reply.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository
        extends JpaRepository<Reply, Long> {

    List<Reply> findAllByComment(Comment comment);
}