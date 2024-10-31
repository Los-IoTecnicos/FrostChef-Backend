package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.CreateInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.CreateInventoryItemResource;

public class CreateInventoryItemCommandFromResourceAssembler {
    public static CreateInventoryItemCommand toCommandFromResource(CreateInventoryItemResource resource) {
        return new CreateInventoryItemCommand(resource.sku(),resource.description(),resource.availableQuantity(),resource.location(),resource.temperatureRange(),resource.project());
    }
}
