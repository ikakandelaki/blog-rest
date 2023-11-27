package com.blog.dto;

import com.blog.entity.Comment;
import com.blog.util.MappingUtil;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;

    public static CommentDto ofEntity(Comment comment) {
        return MappingUtil.map(comment, CommentDto.class);
    }
}
