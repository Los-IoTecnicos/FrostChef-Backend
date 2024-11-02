package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.DeleteInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetAllInventoryItemByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetInventoryItemByIdQuery;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.valueobjects.Project;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.services.InventoryItemCommandService;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.services.InventoryItemQueryService;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.CreateInventoryItemResource;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.InventoryItemResource;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.UpdateInventoryItemResource;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform.CreateInventoryItemCommandFromResourceAssembler;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform.InventoryItemResourceFromEntityAssembler;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform.UpdateInventoryItemCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/inventory-items", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Inventory Items", description = "Inventory Item Management Endpoints")
public class InventoryItemController {

    private final InventoryItemCommandService inventoryItemCommandService;
    private final InventoryItemQueryService inventoryItemQueryService;

    // Constructor con inyección de dependencias
    public InventoryItemController(InventoryItemCommandService inventoryItemCommandService, InventoryItemQueryService inventoryItemQueryService) {
        this.inventoryItemCommandService = inventoryItemCommandService;
        this.inventoryItemQueryService = inventoryItemQueryService;
    }

    // Crear un nuevo item de inventario
    @PostMapping
    public ResponseEntity<InventoryItemResource> createInventoryItem(@RequestBody CreateInventoryItemResource createInventoryItemResource) {
        var createInventoryItemCommand = CreateInventoryItemCommandFromResourceAssembler.toCommandFromResource(createInventoryItemResource);
        var inventoryItemId = inventoryItemCommandService.handle(createInventoryItemCommand);
        if (inventoryItemId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getInventoryItemByIdQuery = new GetInventoryItemByIdQuery(inventoryItemId);
        var inventoryItem = inventoryItemQueryService.handle(getInventoryItemByIdQuery);
        if (inventoryItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var inventoryItemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(inventoryItem.get());
        return new ResponseEntity<>(inventoryItemResource, HttpStatus.CREATED);
    }

    // Obtener un item de inventario por ID
    @GetMapping("/{inventoryItemId}")
    public ResponseEntity<InventoryItemResource> getInventoryItem(@PathVariable Long inventoryItemId) {
        var getInventoryItemByIdQuery = new GetInventoryItemByIdQuery(inventoryItemId);
        var inventoryItem = inventoryItemQueryService.handle(getInventoryItemByIdQuery);
        if (inventoryItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var inventoryItemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(inventoryItem.get());
        return ResponseEntity.ok(inventoryItemResource);
    }

    // Obtener todos los items de inventario por Project ID
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<InventoryItemResource>> getAllInventoryItemsByProjectId(@PathVariable Project projectId) {
        var getAllInventoryItemsByProjectIdQuery = new GetAllInventoryItemByProjectIdQuery(projectId);
        var inventoryItems = inventoryItemQueryService.handle(getAllInventoryItemsByProjectIdQuery);
        var inventoryItemResources = inventoryItems.stream().map(InventoryItemResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(inventoryItemResources);
    }

    // Actualizar un item de inventario por ID
    @PutMapping("/{inventoryItemId}")
    public ResponseEntity<InventoryItemResource> updateInventoryItem(@PathVariable Long inventoryItemId, @RequestBody UpdateInventoryItemResource updateInventoryItemResource) {
        var updateInventoryItemCommand = UpdateInventoryItemCommandFromResourceAssembler.toCommandFromResource(inventoryItemId, updateInventoryItemResource);
        var updatedInventoryItem = inventoryItemCommandService.handle(updateInventoryItemCommand);
        if (updatedInventoryItem.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var inventoryItemResource = InventoryItemResourceFromEntityAssembler.toResourceFromEntity(updatedInventoryItem.get());
        return ResponseEntity.ok(inventoryItemResource);
    }

    // Eliminar un item de inventario por ID
    @DeleteMapping("/{inventoryItemId}")
    public ResponseEntity<?> deleteInventoryItem(@PathVariable Long inventoryItemId) {
        var deleteInventoryItemCommand = new DeleteInventoryItemCommand(inventoryItemId);
        inventoryItemCommandService.handle(deleteInventoryItemCommand);
        return ResponseEntity.ok("Inventory Item deleted successfully");
    }

    // Actualizar la cantidad de un item de inventario
    @PutMapping("/{inventoryItemId}/update-quantity")
    public ResponseEntity<?> updateQuantity(@PathVariable Long inventoryItemId, @RequestParam int newQuantity) {
        var getInventoryItemByIdQuery = new GetInventoryItemByIdQuery(inventoryItemId);
        var inventoryItem = inventoryItemQueryService.handle(getInventoryItemByIdQuery);
        if (inventoryItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        inventoryItemCommandService.updateQuantity(inventoryItemId, newQuantity);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    // Cambiar la ubicación de un item de inventario
    @PutMapping("/{inventoryItemId}/relocate")
    public ResponseEntity<?> relocate(@PathVariable Long inventoryItemId, @RequestParam String newLocation) {
        var getInventoryItemByIdQuery = new GetInventoryItemByIdQuery(inventoryItemId);
        var inventoryItem = inventoryItemQueryService.handle(getInventoryItemByIdQuery);
        if (inventoryItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        inventoryItemCommandService.relocate(inventoryItemId, newLocation);
        return ResponseEntity.ok("Item relocated successfully");
    }

    // Verificar el rango de temperatura de un item de inventario
    @GetMapping("/{inventoryItemId}/check-temperature")
    public ResponseEntity<String> checkTemperature(@PathVariable Long inventoryItemId) {
        var getInventoryItemByIdQuery = new GetInventoryItemByIdQuery(inventoryItemId);
        var inventoryItem = inventoryItemQueryService.handle(getInventoryItemByIdQuery);
        if (inventoryItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        String temperatureRange = inventoryItemCommandService.checkTemperature(inventoryItemId);
        return ResponseEntity.ok("Temperature range: " + temperatureRange);
    }
}
