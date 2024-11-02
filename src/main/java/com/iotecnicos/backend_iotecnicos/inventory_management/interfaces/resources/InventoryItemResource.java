package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources;

public record InventoryItemResource(
        Long id,
        String sku,
        String description,
        int availableQuantity,
        String location,
        String temperatureRange,
        Long project
) {
}
