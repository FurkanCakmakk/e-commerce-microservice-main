package com.example.stock.domain.product.web;

import com.example.stock.domain.product.api.ProductDto;
import com.example.stock.domain.product.api.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(toResponse(service.save(productRequest.toDto())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(toResponse(service.getProduct(id)));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<ProductResponse> responseList = service.getAllProducts().stream()
                .map(productDto -> toResponse(productDto))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(toResponse(service.update(productRequest.toDto(), id)));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        service.delete(id);
        return "Product deleted successfuly";
    }

    public ProductResponse toResponse(ProductDto productDto) {
        return ProductResponse.builder()
                .productId(productDto.getProductId())
                .price(productDto.getPrice())
                .name(productDto.getName())
                .stock(productDto.getStock())
                .categoryDto(productDto.getCategoryDto())
                .build();
    }
}
