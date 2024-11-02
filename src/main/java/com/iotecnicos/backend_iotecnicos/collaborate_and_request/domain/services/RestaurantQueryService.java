package com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.queries.GetAllRestaurantByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.queries.GetRestaurantDetails;

import java.util.List;
import java.util.Optional;

public interface RestaurantQueryService {
    Optional<Restaurant> handle(GetRestaurantDetails query);
    List<Restaurant> handle(GetAllRestaurantByProjectIdQuery query);
}
