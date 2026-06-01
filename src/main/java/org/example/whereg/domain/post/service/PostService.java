package org.example.whereg.domain.post.service;

import org.example.whereg.global.exception.GlobalException;
import org.example.whereg.global.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.post.dto.request.CreatePostRequest;
import org.example.whereg.domain.post.dto.response.PostResponse;
import org.example.whereg.domain.post.entity.ContentType;
import org.example.whereg.domain.post.entity.Post;
import org.example.whereg.domain.post.repository.PostRepository;
import org.example.whereg.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long createPost(CreatePostRequest request, User user) {
        Post post = Post.create(
                user,
                request.contentType(),
                request.title(),
                request.foundPlace(),
                request.photoUrl(),
                request.content()
        );
        return postRepository.save(post).getId();
    }

    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponse::from);
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));
        return PostResponse.from(post);
    }

    public Page<PostResponse> getMyPosts(User user, Pageable pageable) {
        return postRepository.findByAuthor(user, pageable)
                .map(PostResponse::from);
    }

    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.FORBIDDEN);
        }

        postRepository.delete(post);
    }
}