package com.iotecnicos.backend_iotecnicos.inventorr_management.domain.services;

import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.aggregates.CoolingUnit;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.CreateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.DeleteCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.UpdateCoolingUnitCommand;

import java.util.Optional;

public interface CoolingUnitCommandService {
    Long handle (CreateCoolingUnitCommand command);
    Optional<CoolingUnit> handle (UpdateCoolingUnitCommand command);
    void handle (DeleteCoolingUnitCommand command);
}
