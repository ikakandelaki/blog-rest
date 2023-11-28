package com.blog.controller;

import com.blog.dto.CommentDto;
import com.blog.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@SecurityRequirement(name = "Bearer Authentication")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto createComment(@PathVariable long postId, @RequestBody @Valid CommentDto commentDto) {
        return commentService.createComment(postId, commentDto);
    }

    @GetMapping
    public List<CommentDto> getCommentsByPostId(@PathVariable long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/{id}")
    public CommentDto getCommentById(@PathVariable long postId, @PathVariable long id) {
        return commentService.getCommentById(postId, id);
    }

    @PutMapping("/{id}")
    public CommentDto updateComment(
            @PathVariable long postId,
            @PathVariable long id,
            @RequestBody @Valid CommentDto commentDto
    ) {
        return commentService.updateComment(postId, id, commentDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable long postId, @PathVariable long id) {
        commentService.deleteComment(postId, id);
    }
}
