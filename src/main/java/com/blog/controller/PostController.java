package com.blog.controller;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.service.PostService;
import com.blog.util.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@SecurityRequirement(name = "Bearer Authentication")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostDto createPost(@RequestBody @Valid PostDto postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String[] sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable long id) {
        return postService.getPostById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public PostDto updatePost(@PathVariable long id, @RequestBody @Valid PostDto postDto) {
        return postService.updatePost(id, postDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePostById(@PathVariable long id) {
        postService.deletePostById(id);
    }

    @GetMapping("/categories/{categoryId}")
    public List<PostDto> getPostsByCategoryId(@PathVariable Long categoryId) {
        return postService.getPostsByCategoryId(categoryId);
    }
}
