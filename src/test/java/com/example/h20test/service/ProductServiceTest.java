package com.example.h20test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.h20test.domain.entity.HardDrive;
import com.example.h20test.domain.entity.Product;
import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.request.ProductCreateRequest;
import com.example.h20test.dto.response.ProductResponse;
import com.example.h20test.exception.ProductNotFoundException;
import com.example.h20test.mapper.ProductFactory;
import com.example.h20test.mapper.ProductMapper;
import com.example.h20test.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductFactory productFactory;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    void createShouldSaveProductAndReturnResponse() {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.HARD_DRIVE)
                .serialNumber("HDD-001")
                .manufacturer("Seagate")
                .price(new BigDecimal("80.00"))
                .quantity(10)
                .hardDriveCapacityGb(1024)
                .build();
        Product product = new HardDrive("HDD-001", "Seagate", new BigDecimal("80.00"), 10, 1024);
        ProductResponse response = ProductResponse.builder().id(1L).type(ProductType.HARD_DRIVE).build();

        when(productFactory.create(request)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponse(product)).thenReturn(response);

        ProductResponse actual = productService.create(request);

        assertThat(actual).isSameAs(response);
        verify(productRepository).save(product);
    }

    @Test
    void getByIdShouldThrowWhenProductDoesNotExist() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product with id 99 was not found");
    }
}
