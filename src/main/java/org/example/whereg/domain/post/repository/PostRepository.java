package org.example.whereg.domain.post.repository;

import org.example.whereg.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.whereg.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"author"})
    Page<Post> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = {"author"})
    Page<Post> findByAuthor(User author, Pageable pageable);
}