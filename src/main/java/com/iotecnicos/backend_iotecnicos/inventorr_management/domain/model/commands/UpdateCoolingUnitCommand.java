package com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands;

import java.util.Date;

public record UpdateCoolingUnitCommand(Long id, String type, Float capacity, String status, String location, Date installedDate, Date lastMaintenance, Float temperature) {
}
