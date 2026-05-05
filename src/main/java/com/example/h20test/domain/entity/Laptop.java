package com.example.h20test.domain.entity;

import com.example.h20test.domain.enums.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("LAPTOP")
public class Laptop extends Product {

    @Column(name = "laptop_size")
    private Integer laptopSize;

    public Laptop(String serialNumber, String manufacturer, BigDecimal price, Integer quantity, Integer laptopSize) {
        super(ProductType.LAPTOP, serialNumber, manufacturer, price, quantity);
        this.laptopSize = laptopSize;
    }
}
