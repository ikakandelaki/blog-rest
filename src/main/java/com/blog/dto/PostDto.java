package com.blog.dto;

import com.blog.entity.Post;
import com.blog.util.MappingUtil;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private List<CommentDto> comments;

    public static PostDto ofEntity(Post post) {
        return MappingUtil.map(post, PostDto.class);
    }
}
