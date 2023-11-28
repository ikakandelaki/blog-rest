package com.blog.dto;

import com.blog.entity.Category;
import com.blog.util.MappingUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;

    @NotBlank(message = "category name should not be blank")
    private String name;

    @NotBlank(message = "category description should not be blank")
    private String description;

    public static CategoryDto ofEntity(Category category) {
        return MappingUtil.map(category, CategoryDto.class);
    }
}
