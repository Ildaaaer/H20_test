package com.example.h20test.domain.entity;

import com.example.h20test.domain.enums.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING, length = 30)
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false, insertable = false, updatable = false, length = 30)
    private ProductType productType;

    @Column(name = "serial_number", nullable = false, unique = true, length = 100)
    private String serialNumber;

    @Column(name = "manufacturer", nullable = false, length = 100)
    private String manufacturer;

    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    protected Product(ProductType productType, String serialNumber, String manufacturer, BigDecimal price, Integer quantity) {
        this.productType = productType;
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }

    public void updateCommonFields(String serialNumber, String manufacturer, BigDecimal price, Integer quantity) {
        this.serialNumber = serialNumber;
        this.manufacturer = manufacturer;
        this.price = price;
        this.quantity = quantity;
    }
}
