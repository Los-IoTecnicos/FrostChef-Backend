package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources;

public record CreateInventoryItemResource(
        String sku,
        String description,
        int availableQuantity,
        String location,
        String temperatureRange,
        Long project
) {
}
