package com.example.h20test.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.h20test.TestcontainersConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
@Testcontainers(disabledWithoutDocker = true)
class ProductApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void productHappyPathShouldCreateReadUpdateAndFilter() throws Exception {
        String createBody = """
                {
                  "type": "MONITOR",
                  "serialNumber": "MON-E2E-001",
                  "manufacturer": "LG",
                  "price": 350.00,
                  "quantity": 5,
                  "monitorDiagonal": 27.00
                }
                """;

        MvcResult createResult = mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.type").value("MONITOR"))
                .andReturn();

        JsonNode createdProduct = objectMapper.readTree(createResult.getResponse().getContentAsString());
        long productId = createdProduct.get("id").asLong();

        mockMvc.perform(get("/api/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.serialNumber").value("MON-E2E-001"));

        String updateBody = """
                {
                  "type": "MONITOR",
                  "serialNumber": "MON-E2E-001",
                  "manufacturer": "LG",
                  "price": 329.99,
                  "quantity": 8,
                  "monitorDiagonal": 27.00
                }
                """;

        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(329.99))
                .andExpect(jsonPath("$.quantity").value(8));

        mockMvc.perform(get("/api/products").param("type", "MONITOR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("MONITOR"));
    }
}
