package com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.UpdateRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.UpdateRestaurantResource;

public class UpdateRestaurantCommandFromResourceAssembler {
    public static UpdateRestaurantCommand toCommandFromResource(Long restaurantId, UpdateRestaurantResource resource){
        return new UpdateRestaurantCommand(restaurantId,resource.name(), resource.location(),resource.contactInfo(), resource.createdDate());
    }
}
