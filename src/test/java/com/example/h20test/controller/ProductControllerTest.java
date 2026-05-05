package com.example.h20test.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.response.ProductResponse;
import com.example.h20test.exception.ProductNotFoundException;
import com.example.h20test.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getByTypeShouldReturnProducts() throws Exception {
        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .type(ProductType.LAPTOP)
                .serialNumber("LAP-001")
                .manufacturer("Lenovo")
                .price(new BigDecimal("1200.00"))
                .quantity(4)
                .laptopSize(15)
                .build();
        when(productService.getByType(ProductType.LAPTOP)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/products").param("type", "LAPTOP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("LAPTOP"))
                .andExpect(jsonPath("$[0].laptopSize").value(15));
    }

    @Test
    void getByIdShouldReturnNotFound() throws Exception {
        when(productService.getById(42L)).thenThrow(new ProductNotFoundException(42L));

        mockMvc.perform(get("/api/products/42"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product with id 42 was not found"));
    }

    @Test
    void createShouldReturnValidationErrorForMissingLaptopSize() throws Exception {
        String body = """
                {
                  "type": "LAPTOP",
                  "serialNumber": "LAP-001",
                  "manufacturer": "Lenovo",
                  "price": 1200.00,
                  "quantity": 4
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void createShouldReturnCreated() throws Exception {
        ProductResponse response = ProductResponse.builder()
                .id(1L)
                .type(ProductType.HARD_DRIVE)
                .serialNumber("HDD-001")
                .manufacturer("Seagate")
                .price(new BigDecimal("80.00"))
                .quantity(10)
                .hardDriveCapacityGb(1024)
                .build();
        when(productService.create(any())).thenReturn(response);

        String body = """
                {
                  "type": "HARD_DRIVE",
                  "serialNumber": "HDD-001",
                  "manufacturer": "Seagate",
                  "price": 80.00,
                  "quantity": 10,
                  "hardDriveCapacityGb": 1024
                }
                """;

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("HARD_DRIVE"))
                .andExpect(jsonPath("$.hardDriveCapacityGb").value(1024));
    }
}
