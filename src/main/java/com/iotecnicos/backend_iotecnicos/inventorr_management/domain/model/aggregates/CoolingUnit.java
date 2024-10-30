package com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.aggregates;


import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.CreateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.UpdateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.valueobjects.Project;
import com.iotecnicos.backend_iotecnicos.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.Date;

@Getter
@Entity
public class CoolingUnit extends AuditableAbstractAggregateRoot<CoolingUnit> {

    @Embedded
    private Project project;

    @Column
    private String type;

    @Column
    private Float capacity;

    @Column
    private String status;

    @Column
    private String location;

    @Column
    private Date installedDate;

    @Column
    private Date lastMaintenanceDate;
    @Column
    private Float temperature;

    public CoolingUnit() {
        this.project = new Project(null);
        this.type = "";
        this.capacity = 0.0f;
        this.status = "";
        this.location = "";
        this.installedDate = new Date();
        this.lastMaintenanceDate = new Date();
        this.temperature = 0.0f;
    }

    public CoolingUnit(Long project, String type,Float capacity, String status, String location, Date installedDate, Date lastMaintenanceDate, Float temperature) {
        this();
        this.project = new Project(project);
        this.type = type;
        this.capacity = capacity;
        this.status = status;
        this.location = location;
        this.installedDate = installedDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.temperature = temperature;
    }
    public CoolingUnit(CreateCoolingUnitCommand command) {
        this.type=command.type();
        this.capacity = command.capacity();
        this.status = command.status();
        this.location = command.location();
        this.installedDate = command.installedDate();
        this.lastMaintenanceDate=command.lastMaintenance();
        this.temperature = command.temperature();
        this.project=new Project(command.project());
    }
    public CoolingUnit UpdateInformation( String type,Float capacity, String status, String location, Date installedDate, Date lastMaintenanceDate, Float temperature) {
        this.type=type;
        this.capacity = capacity;
        this.status = status;
        this.location = location;
        this.installedDate = installedDate;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.temperature = temperature;
        return this;
    }
    // Método para ajustar la temperatura
    public CoolingUnit adjustTemperature(Float newTemperature) {
        if (newTemperature < 0 || newTemperature > 10) { // Ejemplo de lógica de validación
            throw new IllegalArgumentException("La temperatura debe estar en el rango de 0 a 10 grados.");
        }
        this.temperature = newTemperature;
        return this; // Retorna la instancia actualizada para permitir encadenamiento si es necesario
    }

    // Método para programar el mantenimiento
    public CoolingUnit scheduleMaintenance(Date maintenanceDate) {
        if (maintenanceDate.before(new Date())) { // No se puede programar mantenimiento en una fecha pasada
            throw new IllegalArgumentException("La fecha de mantenimiento no puede estar en el pasado.");
        }
        this.lastMaintenanceDate = maintenanceDate;
        return this;
    }
}
