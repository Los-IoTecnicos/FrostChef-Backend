package com.iotecnicos.backend_iotecnicos.inventory_management.domain.services;


import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.aggregates.CoolingUnit;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetAllCoolerUnitByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetCoolerUnitByIdQuery;
import com.iotecnicos.backend_iotecnicos.inventory_management.domain.model.queries.GetCoolingUnitStatusQuery;

import java.util.List;
import java.util.Optional;

public interface CoolingUnitQueryService {
    Optional<CoolingUnit> handle(GetCoolerUnitByIdQuery query);
    List<CoolingUnit> handle(GetAllCoolerUnitByProjectIdQuery query);
    Optional<String> handle(GetCoolingUnitStatusQuery query);
}
