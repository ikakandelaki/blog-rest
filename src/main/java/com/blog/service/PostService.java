package com.blog.service;

import com.blog.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto getPostById(long id);

    PostDto updatePost(long id, PostDto postDto);

    void deletePostById(long id);
}
