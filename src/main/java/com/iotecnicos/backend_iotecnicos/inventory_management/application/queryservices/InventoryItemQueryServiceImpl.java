package com.iotecnicos.backend_iotecnicos.inventory_management.application.queryservices;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetAllInventoryItemByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetInventoryItemByIdQuery;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.services.InventoryItemQueryService;
import com.iotecnicos.backend_iotecnicos.inventory_management.infraestructure.persistence.jpa.repositories.InventoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemQueryServiceImpl implements InventoryItemQueryService {
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemQueryServiceImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    public Optional<InventoryItem> handle(GetInventoryItemByIdQuery query) {
        return inventoryItemRepository.findById(query.inventoryItemId());
    }

    @Override
    public List<InventoryItem> handle(GetAllInventoryItemByProjectIdQuery query) {
        return inventoryItemRepository.findAllByProject(query.project());
    }
}
