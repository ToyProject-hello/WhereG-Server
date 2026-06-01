package org.example.whereg.domain.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.post.dto.request.CreatePostRequest;
import org.example.whereg.domain.post.dto.response.PostResponse;
import org.example.whereg.domain.post.service.PostService;
import org.example.whereg.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(
            @RequestBody @Valid CreatePostRequest request,
            @AuthenticationPrincipal User user) {
        Long id = postService.createPost(request, user);
        return ResponseEntity.created(URI.create("/api/v1/post/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    @GetMapping("/mypost")
    public ResponseEntity<Page<PostResponse>> getMyPosts(
            @AuthenticationPrincipal User user,
            Pageable pageable) {
        return ResponseEntity.ok(postService.getMyPosts(user, pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user) {
        postService.deletePost(postId, user);
        return ResponseEntity.noContent().build();
    }
}