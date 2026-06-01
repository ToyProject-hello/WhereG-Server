package org.example.whereg.domain.post.repository;

import org.example.whereg.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.example.whereg.domain.user.entity.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author);
}