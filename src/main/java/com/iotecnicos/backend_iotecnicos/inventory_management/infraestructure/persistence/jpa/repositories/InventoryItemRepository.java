package com.iotecnicos.backend_iotecnicos.inventory_management.infraestructure.persistence.jpa.repositories;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.valueobjects.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    boolean existsBySkuAndProject(String sku, Project project);
    boolean existsBySkuAndIdIsNot(String sku, Long id);
    List<InventoryItem> findAllByProject(Project project);
    List<InventoryItem> findAllByLocation(String location);
}
