package com.blog.dto;

import com.blog.entity.Comment;
import com.blog.util.MappingUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    @NotBlank(message = "Commenter name should not be blank")
    private String name;

    @NotBlank(message = "Commenter email should not be blank")
    @Email(message = "Commenter email should be valid")
    private String email;

    @NotBlank(message = "Comment body should not be blank")
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    public static CommentDto ofEntity(Comment comment) {
        return MappingUtil.map(comment, CommentDto.class);
    }
}
