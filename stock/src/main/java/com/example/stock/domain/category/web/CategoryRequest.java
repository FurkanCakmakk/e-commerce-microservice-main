package com.example.stock.domain.category.web;

import com.example.stock.domain.category.api.CategoryDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    private String name;

    public CategoryRequest(@JsonProperty("name") String name){
        this.name=name;
    }

    public CategoryDto toDto(){
        return CategoryDto.builder()
                .name(this.name)
                .build();
    }
}
