package com.iotecnicos.backend_iotecnicos.inventorr_management.infraestructure.persistence.jpa.repositories;

import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.aggregates.CoolingUnit;
import com.iotecnicos.backend_iotecnicos.inventorr_management.domain.model.valueobjects.Project;
import org.springframework.data.jpa.repository.JpaRepository;



import java.util.List;


public interface CoolingUnitRepository extends JpaRepository<CoolingUnit, Long> {

    boolean existsByTypeAndProject(String type, Project project);
    List<CoolingUnit> findAllByProject(Project project);
    List<CoolingUnit> findAllByStatus(String status);

}
