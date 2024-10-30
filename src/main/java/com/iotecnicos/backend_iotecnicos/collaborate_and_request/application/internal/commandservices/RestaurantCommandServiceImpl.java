package com.iotecnicos.backend_iotecnicos.collaborate_and_request.application.internal.commandservices;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.CreateRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.DeleteRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.UpdateRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.valueobjects.Project;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantCommandService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.infrastructure.persistence.jpa.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantCommandServiceImpl implements RestaurantCommandService {
    private final RestaurantRepository restaurantRepository;
    public RestaurantCommandServiceImpl(RestaurantRepository restaurantRepository) {this.restaurantRepository = restaurantRepository;}

    @Override
    public Long handle(CreateRestaurantCommand command) {
        var projectId = new Project(command.project());
        if (restaurantRepository.existsByNameAndProject(command.name(), projectId)){
            throw new IllegalArgumentException("Restaurant with the same name already exists");
        }
        var restaurant = new Restaurant(command);
        try {
            restaurantRepository.save(restaurant);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving restaurant: " + e.getMessage());
        }
        return restaurant.getId();
    }

    @Override
    public Optional<Restaurant> handle(UpdateRestaurantCommand command) {
        if (restaurantRepository.existsByNameAndIdIsNot(command.name(), command.id())){
            throw new IllegalArgumentException("Restaurant with same team name already exists");
        }
        var result = restaurantRepository.findById(command.id());
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Restaurant does not exists");
        }
        var restaurantToUpdate = result.get();
        try{
            var updatedRestaurant = restaurantRepository.save(restaurantToUpdate.updateInformation(command.name(), command.location(), command.contactInfo(), command.createdDate()));
            return Optional.of(updatedRestaurant);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating restaurant: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteRestaurantCommand command) {
        if(!restaurantRepository.existsById(command.restaurantId())){
            throw new IllegalArgumentException("Restaurant does not exist");
        }
        try{
            restaurantRepository.deleteById(command.restaurantId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting restaurant: " + e.getMessage());
        }
    }
    }
}
