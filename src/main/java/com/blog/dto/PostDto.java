package com.blog.dto;

import com.blog.entity.Post;
import com.blog.util.MappingUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private Long id;

    @NotBlank(message = "Post title should not be blank")
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotBlank(message = "Post description should not be blank")
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotBlank(message = "Post content should not be blank")
    private String content;

    @NotNull(message = "category id for post should be provided")
    @Positive(message = "category id for post should be positive number")
    private Long categoryId;

    private List<CommentDto> comments;

    public static PostDto ofEntity(Post post) {
        return MappingUtil.map(post, PostDto.class);
    }
}
