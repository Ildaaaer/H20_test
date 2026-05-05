package com.example.h20test.domain.entity;

import com.example.h20test.domain.enums.DesktopFormFactor;
import com.example.h20test.domain.enums.ProductType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("DESKTOP_COMPUTER")
public class DesktopComputer extends Product {

    @Enumerated(EnumType.STRING)
    @Column(name = "desktop_form_factor", length = 30)
    private DesktopFormFactor desktopFormFactor;

    public DesktopComputer(
            String serialNumber,
            String manufacturer,
            BigDecimal price,
            Integer quantity,
            DesktopFormFactor desktopFormFactor
    ) {
        super(ProductType.DESKTOP_COMPUTER, serialNumber, manufacturer, price, quantity);
        this.desktopFormFactor = desktopFormFactor;
    }
}
