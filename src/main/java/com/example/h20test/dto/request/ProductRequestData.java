package com.example.h20test.dto.request;

import com.example.h20test.domain.enums.DesktopFormFactor;
import com.example.h20test.domain.enums.ProductType;
import java.math.BigDecimal;

public interface ProductRequestData {

    ProductType getType();

    DesktopFormFactor getDesktopFormFactor();

    Integer getLaptopSize();

    BigDecimal getMonitorDiagonal();

    Integer getHardDriveCapacityGb();
}
