package com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.commands;

public class ReportCoolingUnitFailureCommand {
    private final Long coolingUnitId;
    private final String failureDescription;

    public ReportCoolingUnitFailureCommand(Long coolingUnitId, String failureDescription) {
        this.coolingUnitId = coolingUnitId;
        this.failureDescription = failureDescription;
    }

    public Long getCoolingUnitId() {
        return coolingUnitId;
    }

    public String getFailureDescription() {
        return failureDescription;
    }
}
