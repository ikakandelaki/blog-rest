package com.blog.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
}
