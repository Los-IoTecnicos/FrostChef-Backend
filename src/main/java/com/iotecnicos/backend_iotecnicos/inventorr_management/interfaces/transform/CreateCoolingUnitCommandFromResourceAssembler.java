package com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.transform;

import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.CreateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.resources.CreateCoolingUnitResource;

public class CreateCoolingUnitCommandFromResourceAssembler {
    public static CreateCoolingUnitCommand toCommandFromResource(CreateCoolingUnitResource resource) {
        return new CreateCoolingUnitCommand(resource.type(),resource.capacity(),resource.status(),resource.location(),resource.installedDate(),resource.lastMaintenance(),resource.temperature(),resource.project());
    }
}
