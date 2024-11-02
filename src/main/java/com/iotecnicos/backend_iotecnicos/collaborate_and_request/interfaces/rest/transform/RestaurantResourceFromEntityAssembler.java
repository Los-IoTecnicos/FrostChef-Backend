package com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.RestaurantResource;

public class RestaurantResourceFromEntityAssembler {
    public static RestaurantResource toResourceFromEntity(Restaurant entity){
        return new RestaurantResource(entity.getId(), entity.getName(), entity.getLocation(),entity.getContactInfo(),entity.getCreatedDate(), entity.getProjectId());
    }
}
