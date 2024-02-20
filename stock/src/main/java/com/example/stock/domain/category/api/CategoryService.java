package com.example.stock.domain.category.api;

import com.example.stock.domain.category.impl.Category;

import java.util.List;

public interface CategoryService {
    CategoryDto save(CategoryDto categoryDto);
    CategoryDto getCategory(String id);
    List<CategoryDto> getAllCategory();
    CategoryDto update(CategoryDto categoryDto , String id);

    Category getCategoryEntity(String id);
    void delete(String id);

}
