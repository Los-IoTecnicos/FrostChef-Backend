package com.iotecnicos.backend_iotecnicos.inventory_management.domain.services;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.CreateInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.DeleteInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.UpdateInventoryItemCommand;

import java.util.Optional;

public interface InventoryItemCommandService {
    Long handle (CreateInventoryItemCommand command);
    Optional<InventoryItem> handle (UpdateInventoryItemCommand command);
    void handle (DeleteInventoryItemCommand command);

    void updateQuantity(Long inventoryItemId, int newQuantity);
    void relocate(Long inventoryItemId, String newLocation);
    void checkTemperature(Long inventoryItemId);
}
