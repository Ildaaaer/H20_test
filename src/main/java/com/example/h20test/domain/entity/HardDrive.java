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
@DiscriminatorValue("HARD_DRIVE")
public class HardDrive extends Product {

    @Column(name = "hard_drive_capacity_gb")
    private Integer hardDriveCapacityGb;

    public HardDrive(String serialNumber, String manufacturer, BigDecimal price, Integer quantity, Integer hardDriveCapacityGb) {
        super(ProductType.HARD_DRIVE, serialNumber, manufacturer, price, quantity);
        this.hardDriveCapacityGb = hardDriveCapacityGb;
    }
}
