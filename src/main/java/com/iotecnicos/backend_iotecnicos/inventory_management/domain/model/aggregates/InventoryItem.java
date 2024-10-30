package com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.CreateInventoryItemCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.valueobjects.Project;
import com.iotecnicos.backend_iotecnicos.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;



@Getter
@Entity
public class InventoryItem extends AuditableAbstractAggregateRoot<InventoryItem> {

    @Embedded
    private Project project;

    @Column
    private String sku;

    @Column
    private String description;

    @Column
    private int availableQuantity;

    @Column
    private String location;

    @Column
    private String temperatureRange;

    public InventoryItem() {
        this.project = new Project(null);
        this.sku = "";
        this.description = "";
        this.availableQuantity = 0;
        this.location = "";
        this.temperatureRange = "";
    }

    public InventoryItem(Long project, String sku, String description, int availableQuantity, String location, String temperatureRange) {
        this();
        this.project = new Project(project);
        this.sku = sku;
        this.description = description;
        this.availableQuantity = availableQuantity;
        this.location = location;
        this.temperatureRange = temperatureRange;
    }

    public InventoryItem(CreateInventoryItemCommand command) {
        this.sku=command.sku();
        this.description=command.description();
        this.availableQuantity=command.availableQuantity();
        this.location=command.location();
        this.temperatureRange=command.temperatureRange();
        this.project=new Project(command.project());
    }

    public InventoryItem UpdateInformation(String sku, String description, int availableQuantity, String location, String temperatureRange){
        this.sku=sku;
        this.description=description;
        this.availableQuantity=availableQuantity;
        this.location=location;
        this.temperatureRange=temperatureRange;
        return this;
    }
    public Long getProjectId(){
        return project.projectEnt();
    }

    // Métodos adicionales para las funcionalidades específicas

    public InventoryItem updateQuantity(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.availableQuantity = newQuantity;
        return this;
    }

    public InventoryItem relocate(String newLocation) {
        if (newLocation == null || newLocation.isEmpty()) {
            throw new IllegalArgumentException("La nueva ubicación no puede estar vacía.");
        }
        this.location = newLocation;
        return this;
    }

    public void checkTemperature() {
        if (this.temperatureRange == null || this.temperatureRange.isEmpty()) {
            throw new IllegalArgumentException("El rango de temperatura no está definido.");
        }
        // Aquí podrías agregar lógica específica para verificar el rango de temperatura
    }
}

