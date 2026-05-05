package com.example.h20test.mapper;

import com.example.h20test.domain.entity.DesktopComputer;
import com.example.h20test.domain.entity.HardDrive;
import com.example.h20test.domain.entity.Laptop;
import com.example.h20test.domain.entity.Monitor;
import com.example.h20test.domain.entity.Product;
import com.example.h20test.domain.enums.ProductType;
import com.example.h20test.dto.response.ProductResponse;
import com.example.h20test.exception.InvalidProductTypeException;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponse toResponse(Product product) {
        ProductResponse.ProductResponseBuilder builder = ProductResponse.builder()
                .id(product.getId())
                .type(resolveType(product))
                .serialNumber(product.getSerialNumber())
                .manufacturer(product.getManufacturer())
                .price(product.getPrice())
                .quantity(product.getQuantity());

        if (product instanceof DesktopComputer desktopComputer) {
            return builder.desktopFormFactor(desktopComputer.getDesktopFormFactor()).build();
        }
        if (product instanceof Laptop laptop) {
            return builder.laptopSize(laptop.getLaptopSize()).build();
        }
        if (product instanceof Monitor monitor) {
            return builder.monitorDiagonal(monitor.getMonitorDiagonal()).build();
        }
        if (product instanceof HardDrive hardDrive) {
            return builder.hardDriveCapacityGb(hardDrive.getHardDriveCapacityGb()).build();
        }

        throw new InvalidProductTypeException("Unsupported product subtype: " + product.getClass().getSimpleName());
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
        throw new InvalidProductTypeException("Unsupported product subtype: " + product.getClass().getSimpleName());
    }
}
