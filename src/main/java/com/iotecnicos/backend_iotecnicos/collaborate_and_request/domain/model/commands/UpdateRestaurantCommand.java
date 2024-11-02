package com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands;

import java.util.Date;

public record UpdateRestaurantCommand(Long id, String name, String location, String contactInfo, Date createdDate) {
}
