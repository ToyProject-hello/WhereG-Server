package org.example.whereg.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.whereg.domain.comment.dto.request.CreateCommentRequest;
import org.example.whereg.domain.comment.dto.response.CommentResponse;
import org.example.whereg.domain.comment.entity.Comment;
import org.example.whereg.domain.comment.repository.CommentRepository;
import org.example.whereg.domain.post.entity.Post;
import org.example.whereg.domain.post.repository.PostRepository;
import org.example.whereg.domain.user.entity.User;
import org.example.whereg.domain.user.repository.UserRepository;
import org.example.whereg.global.exception.ErrorCode;
import org.example.whereg.global.exception.GlobalException;
import org.example.whereg.global.security.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public List<CommentResponse> getComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostIdAndParentIsNull(postId);
        return comments.stream()
                .map(comment -> {
                    List<CommentResponse> replies = comment.getChildren()
                            .stream()
                            .map(reply -> CommentResponse.from(reply, List.of()))
                            .toList();
                    return CommentResponse.from(comment, replies);
                })
                .toList();
    }

    @Transactional
    public void createComment(Long postId, CreateCommentRequest request, String accessToken) {
        String email = jwtProvider.getEmail(accessToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(ErrorCode.POST_NOT_FOUND));

        Comment parent = null;
        if (request.parentId() != null) {
            parent = commentRepository.findById(request.parentId())
                    .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));
            if (parent.getParent() != null || !parent.getPost().getId().equals(postId)) {
                throw new GlobalException(ErrorCode.INVALID_INPUT);
            }
        }

        commentRepository.save(Comment.create(user, post, parent, request.content()));
    }

    @Transactional
    public void deleteComment(Long commentId, String accessToken) {
        String email = jwtProvider.getEmail(accessToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new GlobalException(ErrorCode.FORBIDDEN);
        }

        commentRepository.delete(comment);
    }

    @Transactional
    public void createReply(Long commentId, CreateCommentRequest request, String accessToken) {
        String email = jwtProvider.getEmail(accessToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        Comment parent = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND));

        if (parent.getParent() != null) {
            throw new GlobalException(ErrorCode.INVALID_INPUT);
        }

        commentRepository.save(Comment.create(user, parent.getPost(), parent, request.content()));
    }
}