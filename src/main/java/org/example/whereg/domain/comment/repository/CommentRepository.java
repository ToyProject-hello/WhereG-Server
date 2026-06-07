package org.example.whereg.domain.comment.repository;

import org.example.whereg.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"author", "children", "children.author"})
    List<Comment> findByPostIdAndParentIsNull(Long postId);
}