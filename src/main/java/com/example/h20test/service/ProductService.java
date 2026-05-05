package com.example.h20test.service;

import com.example.h20test.domain.entity.Product;
import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.request.ProductCreateRequest;
import com.example.h20test.dto.request.ProductUpdateRequest;
import com.example.h20test.dto.response.ProductResponse;
import com.example.h20test.exception.ProductNotFoundException;
import com.example.h20test.mapper.ProductFactory;
import com.example.h20test.mapper.ProductMapper;
import com.example.h20test.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductFactory productFactory;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Product product = productFactory.create(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productFactory.update(product, request);
        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getByType(ProductType type) {
        return productRepository.findByProductType(type)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
