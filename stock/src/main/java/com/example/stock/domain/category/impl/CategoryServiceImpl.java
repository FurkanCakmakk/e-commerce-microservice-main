package com.example.stock.domain.category.impl;

import com.example.stock.domain.category.api.CategoryDto;
import com.example.stock.domain.category.api.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Category category = toEntity(categoryDto);
        category = repository.save(category);
        return toDto(category);
    }

    @Override
    public CategoryDto getCategory(String id) {
        Category category = repository.findById(Long.valueOf(id)).get();
        return toDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        return repository.findAll().stream().map(category -> toDto(category)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String id) {
        Category category = repository.findById(Long.valueOf(id)).get();
        category.setName(categoryDto.getName());
        category = repository.save(category);
        return toDto(category);
    }

    @Override
    public Category getCategoryEntity(String id) {
        Category category= repository.findById(Long.valueOf(id)).get();
        return category;
    }

    @Override
    public void delete(String id) {
        Category category = repository.findById(Long.valueOf(id)).get();
        repository.delete(category);
    }


    CategoryDto toDto(Category entity){
        return CategoryDto.builder()
                .categoryId(entity.getCategoryId())
                .name(entity.getName())
                .build();
    }

    Category toEntity(CategoryDto categoryDto){
        Category category= new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}
