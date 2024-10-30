package com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.CreateRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.CreateRestaurantResource;

public class CreateRestaurantCommandFromResourceAssembler {
    public static CreateRestaurantCommand toCommandFromResource(CreateRestaurantResource resource){
        return new CreateRestaurantCommand(resource.name(), resource.location(),resource.contactInfo(),resource.createdDate(), resource.projectId());
    }
}
