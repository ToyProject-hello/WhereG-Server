package org.example.whereg.domain.comment.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.comment.dto.request.CreateCommentRequest;
import org.example.whereg.domain.comment.dto.response.CommentResponse;
import org.example.whereg.domain.comment.service.CommentService;
import org.example.whereg.global.security.TokenParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final TokenParser tokenParser;

    @GetMapping("/{postId}/comment")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @PostMapping("/{postId}/comment")
    public ResponseEntity<Void> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CreateCommentRequest request,
            HttpServletRequest httpRequest) {
        String accessToken = tokenParser.resolveToken(httpRequest);
        commentService.createComment(postId, request, accessToken);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            HttpServletRequest httpRequest) {
        String accessToken = tokenParser.resolveToken(httpRequest);
        commentService.deleteComment(commentId, accessToken);
        return ResponseEntity.ok().build();
    }
}