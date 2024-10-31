package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.InventoryResource;

public class InventoryItemResourceFromEntityAssembler {
    public static InventoryResource toResourceFromEntity(InventoryItem entity) {
        return new InventoryResource(entity.getId(),entity.getSku(),entity.getDescription(),entity.getAvailableQuantity(),entity.getLocation(),entity.getTemperatureRange(),entity.getProjectId());
    }
}
