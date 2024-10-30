package com.iotecnicos.backend_iotecnicos.inventorr_management.interfaces.resources;

import java.util.Date;

public record UpdateCoolingUnitResource(
        String type,
        Float capacity,
        String status,
        String location,
        Date installedDate,
        Date lastMaintenance,
        Float temperature
) {
}
