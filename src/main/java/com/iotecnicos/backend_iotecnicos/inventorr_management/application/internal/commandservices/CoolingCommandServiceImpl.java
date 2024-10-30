package com.iotecnicos.backend_iotecnicos.inventorr_management.application.internal.commandservices;

import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.aggregates.CoolingUnit;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.CreateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.DeleteCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.commands.UpdateCoolingUnitCommand;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.valueobjects.Project;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.services.CoolingUnitCommandService;
import com.iotecnicos.backend_iotecnicos.inventorr_management.infraestructure.persistence.jpa.repositories.CoolingUnitRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CoolingCommandServiceImpl implements CoolingUnitCommandService {
    private final CoolingUnitRepository coolingUnitRepository;

    public CoolingCommandServiceImpl(CoolingUnitRepository coolingUnitRepository) {
        this.coolingUnitRepository = coolingUnitRepository;
    }

    @Override
    public Long handle(CreateCoolingUnitCommand command) {
        var projectId = new Project(command.project());
        if (coolingUnitRepository.existsByTypeAndProject(command.type(), projectId)) {
            throw new IllegalArgumentException("There is already a cooling unit with this type");
        }
        var coolingUnit= new CoolingUnit(command);
        try{
            coolingUnitRepository.save(coolingUnit);
        }catch (Exception e){
            throw new IllegalArgumentException("There is already a cooling unit with this type"+e.getMessage());
        }
        return coolingUnit.getId();
    }

    @Override
    public Optional<CoolingUnit> handle(UpdateCoolingUnitCommand command) {
        // Buscar si el Cooling Unit existe por su ID
        var result = coolingUnitRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Cooling Unit does not exist with ID " + command.id());
        }
        var coolingUnitToUpdate = result.get();
        // Actualizar la información de la Cooling Unit
        try {
            var updatedCoolingUnit = coolingUnitRepository.save(
                    coolingUnitToUpdate.UpdateInformation(
                            command.type(),
                            command.capacity(),
                            command.status(),
                            command.location(),
                            command.installedDate(),
                            command.lastMaintenance(),
                            command.temperature()
                    )
            );
            return Optional.of(updatedCoolingUnit);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating cooling unit: " + e.getMessage());
        }
    }
    @Override
    public void handle(DeleteCoolingUnitCommand command) {
        if (!coolingUnitRepository.existsById(command.coolingUnitId())){
            throw new IllegalArgumentException("Cooling Unit does not exist with ID ");
        }
        try{
            coolingUnitRepository.deleteById(command.coolingUnitId());
        }catch (Exception e){
            throw new IllegalArgumentException("There is already a cooling unit with this ID"+e.getMessage());
        }
    }
    @Override
    public void adjustTemperature(Long coolingUnitId, Float newTemperature) {
        Optional<CoolingUnit> coolingUnitOptional = coolingUnitRepository.findById(coolingUnitId);
        if (coolingUnitOptional.isEmpty()) {
            throw new IllegalArgumentException("Cooling Unit not found");
        }

        CoolingUnit coolingUnit = coolingUnitOptional.get();

        if (newTemperature < 0 || newTemperature > 10) {
            throw new IllegalArgumentException("La temperatura debe estar en el rango de 0 a 10 grados.");
        }

        coolingUnit.setTemperature(newTemperature);
        coolingUnitRepository.save(coolingUnit);
    }

    @Override
    public void scheduleMaintenance(Long coolingUnitId, Date maintenanceDate) {
        Optional<CoolingUnit> coolingUnitOptional = coolingUnitRepository.findById(coolingUnitId);
        if (coolingUnitOptional.isEmpty()) {
            throw new IllegalArgumentException("Cooling Unit not found");
        }

        CoolingUnit coolingUnit = coolingUnitOptional.get();

        if (maintenanceDate.before(new Date())) {
            throw new IllegalArgumentException("La fecha de mantenimiento no puede estar en el pasado.");
        }

        coolingUnit.setLastMaintenanceDate(maintenanceDate);
        coolingUnitRepository.save(coolingUnit);
    }
}
