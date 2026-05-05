package com.example.h20test.dto.request;

import com.example.h20test.domain.enums.DesktopFormFactor;
import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.validation.ValidProductAttributes;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@ValidProductAttributes
public class ProductUpdateRequest implements ProductRequestData {

    @NotNull
    private ProductType type;

    @NotBlank
    private String serialNumber;

    @NotBlank
    private String manufacturer;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer quantity;

    private DesktopFormFactor desktopFormFactor;

    private Integer laptopSize;

    private BigDecimal monitorDiagonal;

    private Integer hardDriveCapacityGb;
}
