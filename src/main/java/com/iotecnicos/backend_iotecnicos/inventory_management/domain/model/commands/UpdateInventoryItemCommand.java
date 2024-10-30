package com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands;

public record UpdateInventoryItemCommand(Long id, String sky, String description, int availableQuantity, String location,String temperatureRange) {
}
