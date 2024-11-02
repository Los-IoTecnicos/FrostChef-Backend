package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.InventoryItemResource;

public class InventoryItemResourceFromEntityAssembler {
    public static InventoryItemResource toResourceFromEntity(InventoryItem entity) {
        return new InventoryItemResource(entity.getId(),entity.getSku(),entity.getDescription(),entity.getAvailableQuantity(),entity.getLocation(),entity.getTemperatureRange(),entity.getProjectId());
    }
}
