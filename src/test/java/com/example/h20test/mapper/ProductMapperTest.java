package com.example.h20test.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.h20test.domain.entity.Monitor;
import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.response.ProductResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void toResponseShouldMapMonitorSpecificFields() {
        Monitor monitor = new Monitor("MON-001", "LG", new BigDecimal("350.00"), 7, new BigDecimal("27.00"));
        monitor.setId(1L);

        ProductResponse response = productMapper.toResponse(monitor);

        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getType()).isEqualTo(ProductType.MONITOR);
        assertThat(response.getMonitorDiagonal()).isEqualByComparingTo("27.00");
        assertThat(response.getLaptopSize()).isNull();
    }
}
