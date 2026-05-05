package com.example.h20test.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.h20test.TestcontainersConfiguration;
import com.example.h20test.domain.entity.Laptop;
import com.example.h20test.domain.enums.ProductType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Import(TestcontainersConfiguration.class)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByProductTypeShouldReturnOnlyRequestedType() {
        productRepository.saveAndFlush(new Laptop("LAP-001", "Lenovo", new BigDecimal("1200.00"), 3, 15));

        assertThat(productRepository.findByProductType(ProductType.LAPTOP))
                .hasSize(1)
                .first()
                .extracting("serialNumber")
                .isEqualTo("LAP-001");
    }

    @Test
    void saveShouldFailForDuplicateSerialNumber() {
        productRepository.saveAndFlush(new Laptop("LAP-001", "Lenovo", new BigDecimal("1200.00"), 3, 15));

        assertThatThrownBy(() ->
                productRepository.saveAndFlush(new Laptop("LAP-001", "Asus", new BigDecimal("1000.00"), 2, 14))
        ).isInstanceOf(RuntimeException.class);
    }
}
