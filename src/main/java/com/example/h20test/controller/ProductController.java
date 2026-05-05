package com.example.h20test.controller;

import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.request.ProductCreateRequest;
import com.example.h20test.dto.request.ProductUpdateRequest;
import com.example.h20test.dto.response.ProductResponse;
import com.example.h20test.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Computer hardware product API")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create product")
    public ProductResponse create(@Valid @RequestBody ProductCreateRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest request) {
        return productService.update(id, request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by id")
    public ProductResponse getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping
    @Operation(summary = "Get products by type")
    public List<ProductResponse> getByType(@RequestParam ProductType type) {
        return productService.getByType(type);
    }
}
