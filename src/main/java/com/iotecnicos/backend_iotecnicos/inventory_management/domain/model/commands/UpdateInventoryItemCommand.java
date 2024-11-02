package com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands;

public record UpdateInventoryItemCommand(Long id, String sku, String description, int availableQuantity, String location,String temperatureRange) {
}
