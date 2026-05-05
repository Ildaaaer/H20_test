package com.example.h20test.dto.response;

import com.example.h20test.domain.enums.DesktopFormFactor;
import com.example.h20test.domain.enums.ProductType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;

    private ProductType type;

    private String serialNumber;

    private String manufacturer;

    private BigDecimal price;

    private Integer quantity;

    private DesktopFormFactor desktopFormFactor;

    private Integer laptopSize;

    private BigDecimal monitorDiagonal;

    private Integer hardDriveCapacityGb;
}
