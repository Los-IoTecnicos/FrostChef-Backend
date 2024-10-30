package com.iotecnicos.backend_iotecnicos.inventorr_management.application.queryservices;

import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.aggregates.CoolingUnit;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.queries.GetAllCoolerUnitByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.queries.GetCoolerUnitByIdQuery;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.queries.GetCoolingUnitStatusQuery;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.services.CoolingUnitQueryService;
import com.iotecnicos.backend_iotecnicos.inventorr_management.infraestructure.persistence.jpa.repositories.CoolingUnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoolingQueryServiceImpl implements CoolingUnitQueryService {
    private final CoolingUnitRepository coolingUnitRepository;

    public CoolingQueryServiceImpl(CoolingUnitRepository coolingUnitRepository) {
        this.coolingUnitRepository = coolingUnitRepository;
    }

    @Override
    public Optional<CoolingUnit> handle(GetCoolerUnitByIdQuery query) {
        return coolingUnitRepository.findById(query.coolerUnitId());
    }


    @Override
    public List<CoolingUnit> handle(GetAllCoolerUnitByProjectIdQuery query) {
        return coolingUnitRepository.findAllByProject(query.project());
    }


    @Override
    public Optional<String> handle(GetCoolingUnitStatusQuery query) {
        return coolingUnitRepository.findById(query.coolerUnitId())
                .map(CoolingUnit::getStatus);
    }

}
