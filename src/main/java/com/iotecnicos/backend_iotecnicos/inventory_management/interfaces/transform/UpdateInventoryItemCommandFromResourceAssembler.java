package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.UpdateInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.UpdateInventoryItemResource;

public class UpdateInventoryItemCommandFromResourceAssembler {
    public static UpdateInventoryItemCommand toCommandFromResource(Long inventoryId, UpdateInventoryItemResource resource) {
        return new UpdateInventoryItemCommand(inventoryId,resource.sku(),resource.description(),resource.availableQuantity(),resource.location(),resource.temperatureRange());
    }
}
