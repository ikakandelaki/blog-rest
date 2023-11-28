package com.blog.service.impl;

import com.blog.dto.PostDto;
import com.blog.dto.PostResponse;
import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.CategoryRepository;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import com.blog.util.MappingUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Long categoryId = postDto.getCategoryId();
        Category category = getCategoryEntity(categoryId);

        Post post = MappingUtil.map(postDto, Post.class);
        post.setId(null);
        post.setCategory(category);

        Post savedPost = postRepository.save(post);
        return PostDto.ofEntity(savedPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String[] sortBy, String sortDir) {
        Sort sort = Sort.Direction.ASC.name().equalsIgnoreCase(sortDir)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageRequest);
        return getPostResponse(posts);
    }

    private PostResponse getPostResponse(Page<Post> posts) {
        List<PostDto> content = posts
                .stream()
                .map(PostDto::ofEntity)
                .collect(Collectors.toList());
        return PostResponse.builder()
                .content(content)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = getPostEntityById(id);
        return PostDto.ofEntity(post);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = getPostEntityById(id);
        Category category = getCategoryEntity(postDto.getCategoryId());

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        return PostDto.ofEntity(postRepository.save(post));
    }

    @Override
    public void deletePostById(long id) {
        postRepository.delete(getPostEntityById(id));
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
        Category category = getCategoryEntity(categoryId);
        return category.getPosts()
                .stream()
                .map(PostDto::ofEntity)
                .collect(Collectors.toList());
    }

    private Post getPostEntityById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", String.valueOf(id)));
    }

    private Category getCategoryEntity(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(categoryId)));
    }
}
