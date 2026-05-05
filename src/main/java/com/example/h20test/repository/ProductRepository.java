package com.example.h20test.repository;

import com.example.h20test.domain.entity.Product;
import com.example.h20test.domain.enums.ProductType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByProductType(ProductType productType);
}
