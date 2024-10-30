package com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces;

import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.DeleteCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.queries.GetAllCoolerUnitByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.queries.GetCoolerUnitByIdQuery;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.services.CoolingUnitCommandService;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.services.CoolingUnitQueryService;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.resources.CoolingUnitResource;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.resources.CreateCoolingUnitResource;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.resources.UpdateCoolingUnitResource;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.transform.CoolingUnitResourceFromEntityAssembler;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.transform.CreateCoolingUnitCommandFromResourceAssembler;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.transform.UpdateCoolingUnitCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cooling-units", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Cooling Units", description = "Cooling Unit Management Endpoints")
public class CoolingUnitController {

    private final CoolingUnitCommandService coolingUnitCommandService;
    private final CoolingUnitQueryService coolingUnitQueryService;

    // Constructor con inyección de dependencias
    public CoolingUnitController(CoolingUnitCommandService coolingUnitCommandService, CoolingUnitQueryService coolingUnitQueryService) {
        this.coolingUnitCommandService = coolingUnitCommandService;
        this.coolingUnitQueryService = coolingUnitQueryService;
    }

    // Crear una nueva unidad de refrigeración
    @PostMapping
    public ResponseEntity<CoolingUnitResource> createCoolingUnit(@RequestBody CreateCoolingUnitResource createCoolingUnitResource) {
        var createCoolingUnitCommand = CreateCoolingUnitCommandFromResourceAssembler.toCommandFromResource(createCoolingUnitResource);
        var coolingUnitId = coolingUnitCommandService.handle(createCoolingUnitCommand);
        if (coolingUnitId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getCoolingUnitByIdQuery = new GetCoolerUnitByIdQuery(coolingUnitId);
        var coolingUnit = coolingUnitQueryService.handle(getCoolingUnitByIdQuery);
        if (coolingUnit.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var coolingUnitResource = CoolingUnitResourceFromEntityAssembler.toResourceFromEntity(coolingUnit.get());
        return new ResponseEntity<>(coolingUnitResource, HttpStatus.CREATED);
    }

    // Obtener una unidad de refrigeración por ID
    @GetMapping("/{coolingUnitId}")
    public ResponseEntity<CoolingUnitResource> getCoolingUnit(@PathVariable Long coolingUnitId) {
        var getCoolingUnitByIdQuery = new GetCoolerUnitByIdQuery(coolingUnitId);
        var coolingUnit = coolingUnitQueryService.handle(getCoolingUnitByIdQuery);
        if (coolingUnit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var coolingUnitResource = CoolingUnitResourceFromEntityAssembler.toResourceFromEntity(coolingUnit.get());
        return ResponseEntity.ok(coolingUnitResource);
    }

    // Obtener todas las unidades de refrigeración por Project ID
    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<CoolingUnitResource>> getAllCoolingUnitsByProjectId(@PathVariable Long projectId) {
        var getAllCoolingUnitsByProjectIdQuery = new GetAllCoolerUnitByProjectIdQuery(projectId);
        var coolingUnits = coolingUnitQueryService.handle(getAllCoolingUnitsByProjectIdQuery);
        var coolingUnitResources = coolingUnits.stream().map(CoolingUnitResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(coolingUnitResources);
    }

    // Actualizar una unidad de refrigeración por ID
    @PutMapping("/{coolingUnitId}")
    public ResponseEntity<CoolingUnitResource> updateCoolingUnit(@PathVariable Long coolingUnitId, @RequestBody UpdateCoolingUnitResource updateCoolingUnitResource) {
        var updateCoolingUnitCommand = UpdateCoolingUnitCommandFromResourceAssembler.toCommandFromResource(coolingUnitId, updateCoolingUnitResource);
        var updatedCoolingUnit = coolingUnitCommandService.handle(updateCoolingUnitCommand);
        if (updatedCoolingUnit.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var coolingUnitResource = CoolingUnitResourceFromEntityAssembler.toResourceFromEntity(updatedCoolingUnit.get());
        return ResponseEntity.ok(coolingUnitResource);
    }

    // Eliminar una unidad de refrigeración por ID
    @DeleteMapping("/{coolingUnitId}")
    public ResponseEntity<?> deleteCoolingUnit(@PathVariable Long coolingUnitId) {
        var deleteCoolingUnitCommand = new DeleteCoolingUnitCommand(coolingUnitId);
        coolingUnitCommandService.handle(deleteCoolingUnitCommand);
        return ResponseEntity.ok("Cooling Unit deleted successfully");
    }

    // Ajustar la temperatura de una unidad de refrigeración
    @PutMapping("/{coolingUnitId}/adjust-temperature")
    public ResponseEntity<?> adjustTemperature(@PathVariable Long coolingUnitId, @RequestParam Float newTemperature) {
        var getCoolingUnitByIdQuery = new GetCoolerUnitByIdQuery(coolingUnitId);
        var coolingUnit = coolingUnitQueryService.handle(getCoolingUnitByIdQuery);
        if (coolingUnit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        coolingUnitCommandService.adjustTemperature(coolingUnitId, newTemperature);
        return ResponseEntity.ok("Temperature adjusted successfully");
    }

    // Programar el mantenimiento de una unidad de refrigeración
    @PostMapping("/{coolingUnitId}/schedule-maintenance")
    public ResponseEntity<?> scheduleMaintenance(@PathVariable Long coolingUnitId, @RequestParam Date maintenanceDate) {
        var getCoolingUnitByIdQuery = new GetCoolerUnitByIdQuery(coolingUnitId);
        var coolingUnit = coolingUnitQueryService.handle(getCoolingUnitByIdQuery);
        if (coolingUnit.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        coolingUnitCommandService.scheduleMaintenance(coolingUnitId, maintenanceDate);
        return ResponseEntity.ok("Maintenance scheduled successfully");
    }
}