package com.example.h20test.mapper;

import com.example.h20test.domain.entity.DesktopComputer;
import com.example.h20test.domain.entity.HardDrive;
import com.example.h20test.domain.entity.Laptop;
import com.example.h20test.domain.entity.Monitor;
import com.example.h20test.domain.entity.Product;
import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.request.ProductCreateRequest;
import com.example.h20test.dto.request.ProductRequestData;
import com.example.h20test.dto.request.ProductUpdateRequest;
import com.example.h20test.exception.BusinessRuleViolationException;
import org.springframework.stereotype.Component;

@Component
public class ProductFactory {

    public Product create(ProductCreateRequest request) {
        return switch (request.getType()) {
            case DESKTOP_COMPUTER -> new DesktopComputer(
                    request.getSerialNumber(),
                    request.getManufacturer(),
                    request.getPrice(),
                    request.getQuantity(),
                    request.getDesktopFormFactor()
            );
            case LAPTOP -> new Laptop(
                    request.getSerialNumber(),
                    request.getManufacturer(),
                    request.getPrice(),
                    request.getQuantity(),
                    request.getLaptopSize()
            );
            case MONITOR -> new Monitor(
                    request.getSerialNumber(),
                    request.getManufacturer(),
                    request.getPrice(),
                    request.getQuantity(),
                    request.getMonitorDiagonal()
            );
            case HARD_DRIVE -> new HardDrive(
                    request.getSerialNumber(),
                    request.getManufacturer(),
                    request.getPrice(),
                    request.getQuantity(),
                    request.getHardDriveCapacityGb()
            );
        };
    }

    public void update(Product product, ProductUpdateRequest request) {
        ProductType existingType = resolveType(product);
        if (existingType != request.getType()) {
            throw new BusinessRuleViolationException("Changing product type is not supported");
        }

        product.updateCommonFields(
                request.getSerialNumber(),
                request.getManufacturer(),
                request.getPrice(),
                request.getQuantity()
        );
        updateSpecificAttributes(product, request);
    }

    private void updateSpecificAttributes(Product product, ProductRequestData request) {
        switch (request.getType()) {
            case DESKTOP_COMPUTER -> ((DesktopComputer) product).setDesktopFormFactor(request.getDesktopFormFactor());
            case LAPTOP -> ((Laptop) product).setLaptopSize(request.getLaptopSize());
            case MONITOR -> ((Monitor) product).setMonitorDiagonal(request.getMonitorDiagonal());
            case HARD_DRIVE -> ((HardDrive) product).setHardDriveCapacityGb(request.getHardDriveCapacityGb());
        }
    }

    private ProductType resolveType(Product product) {
        if (product instanceof DesktopComputer) {
            return ProductType.DESKTOP_COMPUTER;
        }
        if (product instanceof Laptop) {
            return ProductType.LAPTOP;
        }
        if (product instanceof Monitor) {
            return ProductType.MONITOR;
        }
        if (product instanceof HardDrive) {
            return ProductType.HARD_DRIVE;
        }
        throw new BusinessRuleViolationException("Unsupported product subtype: " + product.getClass().getSimpleName());
    }
}
