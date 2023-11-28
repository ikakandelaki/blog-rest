package com.blog.service.impl;

import com.blog.dto.CategoryDto;
import com.blog.entity.Category;
import com.blog.exception.ResourceNotFoundException;
import com.blog.repository.CategoryRepository;
import com.blog.service.CategoryService;
import com.blog.util.MappingUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = MappingUtil.map(categoryDto, Category.class);
        category.setId(null);
        Category savedCategory = categoryRepository.save(category);
        return CategoryDto.ofEntity(savedCategory);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = getCategoryEntity(id);
        return CategoryDto.ofEntity(category);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDto::ofEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category category = getCategoryEntity(id);
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        return CategoryDto.ofEntity(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = getCategoryEntity(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", String.valueOf(id)));
    }
}
