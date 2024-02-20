package com.example.stock.domain.product.impl;

import com.example.stock.domain.category.api.CategoryDto;
import com.example.stock.domain.category.api.CategoryService;
import com.example.stock.domain.product.api.ProductDto;
import com.example.stock.domain.product.api.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final CategoryService categoryService;
    private final ProductRepository repository;

    public ProductServiceImpl(CategoryService categoryService, ProductRepository repository) {
        this.categoryService = categoryService;
        this.repository = repository;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        Product product = toEntity(productDto);
        product = repository.save(product);

        return toDto(product);
    }

    @Override
    public ProductDto getProduct(String id) {
        Product product = repository.findById(Long.valueOf(id)).get();

        return toDto(product);
    }

    @Override
    public ProductDto update(ProductDto productDto, String id) {
        Product product = repository.findById(Long.valueOf(id)).get();
        product.setStock(productDto.getStock());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.getCategoryEntity(String.valueOf(productDto.getCategoryDto().getCategoryId())));
        product = repository.save(product);
        return toDto(product);
    }

    @Override
    public void delete(String id) {
        Product product = repository.findById(Long.valueOf(id)).get();
        repository.delete(product);

    }

    @Override
    public List<ProductDto> getAllProducts() {
        return repository.findAll().stream()
                .map(product -> toDto(product))
                .collect(Collectors.toList());
    }

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .price(product.getPrice())
                .productId(product.getProductId())
                .name(product.getName())
                .stock(product.getStock())
                .categoryDto(CategoryDto.builder()
                        .categoryId(product.getCategory().getCategoryId())
                        .name(product.getCategory().getName())
                        .build())
                .build();
    }

    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setCategory(categoryService.getCategoryEntity(String.valueOf(productDto.getCategoryDto().getCategoryId())));
        return product;

    }
}
