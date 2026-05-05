package com.example.h20test.validation;

import com.example.h20test.dto.request.ProductRequestData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class ProductAttributesValidator implements ConstraintValidator<ValidProductAttributes, ProductRequestData> {

    @Override
    public boolean isValid(ProductRequestData request, ConstraintValidatorContext context) {
        if (request == null || request.getType() == null) {
            return true;
        }

        return switch (request.getType()) {
            case DESKTOP_COMPUTER -> hasOnlyDesktopAttributes(request);
            case LAPTOP -> hasOnlyLaptopAttributes(request);
            case MONITOR -> hasOnlyMonitorAttributes(request);
            case HARD_DRIVE -> hasOnlyHardDriveAttributes(request);
        };
    }

    private boolean hasOnlyDesktopAttributes(ProductRequestData request) {
        return request.getDesktopFormFactor() != null
                && request.getLaptopSize() == null
                && request.getMonitorDiagonal() == null
                && request.getHardDriveCapacityGb() == null;
    }

    private boolean hasOnlyLaptopAttributes(ProductRequestData request) {
        Integer laptopSize = request.getLaptopSize();
        return request.getDesktopFormFactor() == null
                && isAllowedLaptopSize(laptopSize)
                && request.getMonitorDiagonal() == null
                && request.getHardDriveCapacityGb() == null;
    }

    private boolean hasOnlyMonitorAttributes(ProductRequestData request) {
        return request.getDesktopFormFactor() == null
                && request.getLaptopSize() == null
                && isPositive(request.getMonitorDiagonal())
                && request.getHardDriveCapacityGb() == null;
    }

    private boolean hasOnlyHardDriveAttributes(ProductRequestData request) {
        Integer capacity = request.getHardDriveCapacityGb();
        return request.getDesktopFormFactor() == null
                && request.getLaptopSize() == null
                && request.getMonitorDiagonal() == null
                && capacity != null
                && capacity > 0;
    }

    private boolean isAllowedLaptopSize(Integer laptopSize) {
        return laptopSize != null
                && (laptopSize == 13 || laptopSize == 14 || laptopSize == 15 || laptopSize == 17);
    }

    private boolean isPositive(BigDecimal value) {
        return value != null && value.compareTo(BigDecimal.ZERO) > 0;
    }
}
