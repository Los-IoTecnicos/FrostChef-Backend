package com.iotecnicos.backend_iotecnicos.collaborate_and_request.application.internal.queryservices;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.queries.GetAllRestaurantByProjectIdQuery;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.queries.GetRestaurantDetails;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantQueryService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.infrastructure.persistence.jpa.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantQueryServiceImpl implements RestaurantQueryService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantQueryServiceImpl(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Optional<Restaurant> handle(GetRestaurantDetails query){
        return restaurantRepository.findById(query.restaurantId());
    }

    @Override
    public List<Restaurant> handle(GetAllRestaurantByProjectIdQuery query) {
        return restaurantRepository.findAllByProject(query.project());
    }
}
