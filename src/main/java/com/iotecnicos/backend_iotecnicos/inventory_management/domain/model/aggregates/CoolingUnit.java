package com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates;


import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands.CreateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.valueobjects.Project;
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
    @Column
    private String failureDescription;
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

    public Long getProjectId(){
        return project.projectEnt();
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public void setLastMaintenanceDate(Date lastMaintenanceDate) {this.lastMaintenanceDate = lastMaintenanceDate;}
    // Getter y Setter para failureDescription
    public String getFailureDescription() {
        return failureDescription;
    }

    public void setFailureDescription(String failureDescription) {
        this.failureDescription = failureDescription;
    }
}
