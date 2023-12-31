package com.blog.service.impl;

import com.blog.dto.CommentDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.BlogException;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import com.blog.util.MappingUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = MappingUtil.map(commentDto, Comment.class);
        comment.setId(null);
        Post post = getPostById(postId);
        comment.setPost(post);
        return CommentDto.ofEntity(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        return getPostById(postId)
                .getComments()
                .stream()
                .map(CommentDto::ofEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Comment comment = getCommentEntityById(postId, commentId);
        return CommentDto.ofEntity(comment);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Comment comment = getCommentEntityById(postId, commentId);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return CommentDto.ofEntity(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Comment comment = getCommentEntityById(postId, commentId);
        commentRepository.delete(comment);
    }

    private Comment getCommentEntityById(long postId, long commentId) {
        Post post = getPostById(postId);
        Comment comment = getCommentById(commentId);

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }

    private Post getPostById(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(postId)));
    }

    private Comment getCommentById(long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", String.valueOf(commentId)));
    }
}
