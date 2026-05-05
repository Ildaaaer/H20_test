package com.example.h20test.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.h20test.domain.entity.Laptop;
import com.example.h20test.domain.entity.Product;
import com.example.h20test.domain.enums.DesktopFormFactor;
import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.request.ProductCreateRequest;
import com.example.h20test.dto.request.ProductUpdateRequest;
import com.example.h20test.exception.BusinessRuleViolationException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductFactoryTest {

    private final ProductFactory productFactory = new ProductFactory();

    @Test
    void createShouldBuildLaptopSubtype() {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .type(ProductType.LAPTOP)
                .serialNumber("LAP-001")
                .manufacturer("Lenovo")
                .price(new BigDecimal("1200.00"))
                .quantity(5)
                .laptopSize(15)
                .build();

        Product product = productFactory.create(request);

        assertThat(product).isInstanceOf(Laptop.class);
        assertThat(product.getSerialNumber()).isEqualTo("LAP-001");
        assertThat(((Laptop) product).getLaptopSize()).isEqualTo(15);
    }

    @Test
    void updateShouldRejectProductTypeChange() {
        Product product = new Laptop("LAP-001", "Lenovo", new BigDecimal("1200.00"), 5, 15);
        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .type(ProductType.DESKTOP_COMPUTER)
                .serialNumber("PC-001")
                .manufacturer("Dell")
                .price(new BigDecimal("900.00"))
                .quantity(3)
                .desktopFormFactor(DesktopFormFactor.DESKTOP)
                .build();

        assertThatThrownBy(() -> productFactory.update(product, request))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessage("Changing product type is not supported");
    }
}
