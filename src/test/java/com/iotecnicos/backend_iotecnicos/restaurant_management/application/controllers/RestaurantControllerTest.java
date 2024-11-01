package com.iotecnicos.backend_iotecnicos.restaurant_management.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.CreateRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.queries.GetRestaurantDetails;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantCommandService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantQueryService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.RestaurantController;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.CreateRestaurantResource;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.RestaurantResource;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform.CreateRestaurantCommandFromResourceAssembler;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform.RestaurantResourceFromEntityAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RestaurantControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RestaurantCommandService restaurantCommandService;

    @Mock
    private RestaurantQueryService restaurantQueryService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    void testGetRestaurantById() throws Exception {
        Long restaurantId = 1L;
        Restaurant restaurant = new Restaurant("My Restaurant", "My Location", "123-456-789", new Date(), 1L);
        when(restaurantQueryService.handle(any(GetRestaurantDetails.class))).thenReturn(Optional.of(restaurant));

        mockMvc.perform(get("/api/v1/restaurant/{restaurantId}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("My Restaurant"))
                .andExpect(jsonPath("$.location").value("My Location"))
                .andExpect(jsonPath("$.contactInfo").value("123-456-789"));
    }

    @Test
    void testCreateRestaurant() throws Exception {
        // Crear un recurso de entrada para el restaurante
        CreateRestaurantResource createRestaurantResource = new CreateRestaurantResource();
        createRestaurantResource.setName("Test Restaurant");
        createRestaurantResource.setLocation("Test Location");
        createRestaurantResource.setContactInfo("123-456-789");

        // Convertir el objeto a JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String restaurantJson = objectMapper.writeValueAsString(createRestaurantResource);

        // Simular comportamiento del comando y consulta
        Long restaurantId = 1L;
        when(restaurantCommandService.handle(any(CreateRestaurantCommand.class))).thenReturn(restaurantId);
        Restaurant restaurant = new Restaurant("Test Restaurant", "Test Location", "123-456-789", new Date(), 1L);
        when(restaurantQueryService.handle(any())).thenReturn(Optional.of(restaurant));

        // Ejecutar la petición POST y validar la respuesta
        mockMvc.perform(post("/api/v1/restaurant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(restaurantJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Restaurant"))
                .andExpect(jsonPath("$.location").value("Test Location"))
                .andExpect(jsonPath("$.contactInfo").value("123-456-789"));
    }
}
