package com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands;

import java.util.Date;

public record CreateCoolingUnitCommand(String type, Float capacity, String status, String location, Date installedDate, Date lastMaintenance, Float temperature, Long project) {
}
