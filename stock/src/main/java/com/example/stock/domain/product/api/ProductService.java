package com.example.stock.domain.product.api;

import java.util.List;

public interface ProductService {
    ProductDto save(ProductDto productDto);
    ProductDto getProduct(String id);
    ProductDto update(ProductDto productDto, String id);
    void delete(String id);

    List<ProductDto> getAllProducts();

}
