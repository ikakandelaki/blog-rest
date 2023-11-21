package com.blog.service.impl;

import com.blog.dto.PostDto;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapPostDtoToEntity(postDto);
        Post savedPost = postRepository.save(post);
        return mapPostEntityToDto(savedPost);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(this::mapPostEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = getPostEntityById(id);
        return mapPostEntityToDto(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = getPostEntityById(id);
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return mapPostEntityToDto(postRepository.save(post));
    }

    @Override
    public void deletePostById(long id) {
        postRepository.delete(getPostEntityById(id));
    }

    private Post getPostEntityById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
    }

    private Post mapPostDtoToEntity(PostDto postDto) {
        return new Post(
                postDto.getTitle(),
                postDto.getDescription(),
                postDto.getContent()
        );
    }

    private PostDto mapPostEntityToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .content(post.getContent())
                .build();
    }
}
