package com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.transform;

import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.UpdateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.resources.UpdateCoolingUnitResource;

public class UpdateCoolingUnitCommandFromResourceAssembler {

    public static UpdateCoolingUnitCommand toCommandFromResource(Long coolingUnitId, UpdateCoolingUnitResource resource) {
        return new UpdateCoolingUnitCommand(coolingUnitId, resource.type(),resource.capacity(),resource.status(),resource.location(),resource.installedDate(),resource.lastMaintenance(),resource.temperature());
    }
}
