package com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.transform;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.CoolingUnit;
import com.iotecnicos.backend_iotecnicos.inventory_management.interfaces.resources.CoolingUnitResource;

public class CoolingUnitResourceFromEntityAssembler {
    public static CoolingUnitResource toResourceFromEntity(CoolingUnit entity) {
        return new CoolingUnitResource(entity.getId(),entity.getType(),entity.getCapacity(),entity.getStatus(),entity.getLocation(),entity.getInstalledDate(),entity.getLastMaintenanceDate(),entity.getTemperature(),entity.getProjectId());
    }
}
