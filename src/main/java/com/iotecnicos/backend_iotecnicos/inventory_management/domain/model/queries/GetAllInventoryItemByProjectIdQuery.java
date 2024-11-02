package com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries;

import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.valueobjects.Project;

public record GetAllInventoryItemByProjectIdQuery(Project project) {
}
