package com.iotecnicos.backend_iotecnicos.inventory_management.application.internal.commandservices;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.InventoryItem;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.CreateInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.DeleteInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.UpdateInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.valueobjects.Project;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.services.InventoryItemCommandService;
import com.iotecnicos.backend_iotecnicos.inventory_management.infraestructure.persistence.jpa.repositories.InventoryItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryItemCommandServiceImpl implements InventoryItemCommandService {
    private final InventoryItemRepository inventoryItemRepository;

    public InventoryItemCommandServiceImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @Override
    public Long handle(CreateInventoryItemCommand command) {
        var projectId = new Project(command.project());
        if (inventoryItemRepository.existsBySkuAndProject(command.sku(), projectId)) {
            throw new IllegalArgumentException("There is already an inventory item with this SKU in the project");
        }
        var inventoryItem = new InventoryItem(command);
        try {
            inventoryItemRepository.save(inventoryItem);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving inventory item: " + e.getMessage());
        }
        return inventoryItem.getId();
    }

    @Override
    public Optional<InventoryItem> handle(UpdateInventoryItemCommand command) {
        if(inventoryItemRepository.existsBySkuAndIdIsNot(command.sku(), command.id())){
            throw new IllegalArgumentException("There is already an inventory item with this SKU in the project");
        }
        var result = inventoryItemRepository.findById(command.id());
        if (result.isEmpty()){
            throw new IllegalArgumentException("There is no an inventory item with this ID in the project");
        }
        var inventoryItemToUpdate = result.get();
        try {
            var updatedInventoryItem = inventoryItemRepository.save(inventoryItemToUpdate.UpdateInformation(command.sku(),command.description(),command.availableQuantity(),command.location(),command.temperatureRange()));
            return Optional.of(updatedInventoryItem);
        }catch (Exception e){
            throw new IllegalArgumentException("Error while saving inventory item: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteInventoryItemCommand command) {
        if (!inventoryItemRepository.existsById(command.inventoryItemId())) {
            throw new IllegalArgumentException("Inventory Item does not exist with ID " + command.inventoryItemId());
        }
        try {
            inventoryItemRepository.deleteById(command.inventoryItemId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting inventory item: " + e.getMessage());
        }
    }

    @Override
    public void updateQuantity(Long inventoryItemId, int newQuantity) {
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findById(inventoryItemId);
        if (inventoryItemOptional.isEmpty()) {
            throw new IllegalArgumentException("Inventory Item not found with ID " + inventoryItemId);
        }

        InventoryItem inventoryItem = inventoryItemOptional.get();
        inventoryItem.updateQuantity(newQuantity);  // Usar el método con nombre original
        inventoryItemRepository.save(inventoryItem);
    }

    @Override
    public void relocate(Long inventoryItemId, String newLocation) {
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findById(inventoryItemId);
        if (inventoryItemOptional.isEmpty()) {
            throw new IllegalArgumentException("Inventory Item not found with ID " + inventoryItemId);
        }

        InventoryItem inventoryItem = inventoryItemOptional.get();
        inventoryItem.relocate(newLocation);  // Usar el método con nombre original
        inventoryItemRepository.save(inventoryItem);
    }
    @Override
    public String checkTemperature(Long inventoryItemId) {
        Optional<InventoryItem> inventoryItemOptional = inventoryItemRepository.findById(inventoryItemId);
        if (inventoryItemOptional.isEmpty()) {
            throw new IllegalArgumentException("Inventory Item not found with ID " + inventoryItemId);
        }

        InventoryItem inventoryItem = inventoryItemOptional.get();
        return inventoryItem.getTemperatureRange();
    }

}
