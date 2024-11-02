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

    // Métodos adicionales con los nombres originales
    public void updateQuantity(int newQuantity) {
        this.availableQuantity = newQuantity;
    }

    public void relocate(String newLocation) {
        this.location = newLocation;
    }

    public String checkTemperature() {
        return this.temperatureRange;
    }

}

