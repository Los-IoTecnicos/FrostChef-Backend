package com.iotecnicos.backend_iotecnicos.inventory_management.domain.services;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetAllInventoryItemByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetInventoryItemByIdQuery;

import java.util.List;
import java.util.Optional;

public interface InventoryItemQueryService {
    Optional<InventoryItem> handle(GetInventoryItemByIdQuery query);
    List<InventoryItem> handle(GetAllInventoryItemByProjectIdQuery query);
}
