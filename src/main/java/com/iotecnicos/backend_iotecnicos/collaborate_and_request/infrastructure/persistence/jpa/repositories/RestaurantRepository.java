package com.iotecnicos.backend_iotecnicos.collaborate_and_request.infrastructure.persistence.jpa.repositories;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.valueobjects.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByNameAndProject(String name, Project project);
    boolean existsByNameAndIdIsNot(String name, Long id);
    List<Restaurant> findAllByProject(Project Project);
}
