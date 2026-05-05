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
@DiscriminatorValue("MONITOR")
public class Monitor extends Product {

    @Column(name = "monitor_diagonal", precision = 5, scale = 2)
    private BigDecimal monitorDiagonal;

    public Monitor(String serialNumber, String manufacturer, BigDecimal price, Integer quantity, BigDecimal monitorDiagonal) {
        super(ProductType.MONITOR, serialNumber, manufacturer, price, quantity);
        this.monitorDiagonal = monitorDiagonal;
    }
}
