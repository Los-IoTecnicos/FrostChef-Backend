package com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.CreateRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.DeleteRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.UpdateRestaurantCommand;

import java.util.Optional;

public interface RestaurantCommandService {
    Long handle(CreateRestaurantCommand command);
    Optional<Restaurant> handle(UpdateRestaurantCommand command);
    void handle(DeleteRestaurantCommand command);
}
