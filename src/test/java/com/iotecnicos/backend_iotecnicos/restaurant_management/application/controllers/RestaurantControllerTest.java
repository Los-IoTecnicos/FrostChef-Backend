package com.iotecnicos.backend_iotecnicos.restaurant_management.application.controllers;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.aggregates.Restaurant;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.DeleteRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.queries.GetRestaurantDetails;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantCommandService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantQueryService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.RestaurantController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class RestaurantControllerTest {

    @Mock
    private RestaurantCommandService restaurantCommandService;

    @Mock
    private RestaurantQueryService restaurantQueryService;

    @InjectMocks
    private RestaurantController restaurantController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    public void testGetRestaurantById() throws Exception {
        Long restaurantId = 1L;

        // Crear y configurar el restaurante mock
        Restaurant restaurant = new Restaurant();
        restaurant.setName("My Restaurant");
        restaurant.setLocation("My Location");
        restaurant.setContactInfo("123-456-789");

        // Usar ReflectionTestUtils si no tienes un setter para el ID
        ReflectionTestUtils.setField(restaurant, "id", restaurantId);

        // Configurar el mock para que devuelva el restaurante esperado
        GetRestaurantDetails query = new GetRestaurantDetails(restaurantId);
        when(restaurantQueryService.handle(query)).thenReturn(Optional.of(restaurant));

        // Realizar la solicitud y verificar los resultados
        mockMvc.perform(get("/api/v1/restaurant/{restaurantId}", restaurantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("My Restaurant"))
                .andExpect(jsonPath("$.location").value("My Location"))
                .andExpect(jsonPath("$.contactInfo").value("123-456-789"));
    }

    @Test
    public void testCreateRestaurant() throws Exception {
        // Configura los datos para el test
        String jsonRequest = "{\"name\": \"New Restaurant\", \"location\": \"New Location\"}";

        mockMvc.perform(post("/api/v1/restaurants")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateRestaurant() throws Exception {
        Long restaurantId = 1L;
        String jsonRequest = "{\"name\": \"Updated Restaurant\", \"location\": \"Updated Location\"}";

        mockMvc.perform(put("/api/v1/restaurants/{restaurantId}", restaurantId)
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRestaurant() throws Exception {
        Long restaurantId = 1L;

        // Simulación de comportamiento con Mockito
        doNothing().when(restaurantCommandService).handle(any(DeleteRestaurantCommand.class));

        // Realización de la petición DELETE y verificación de la respuesta
        mockMvc.perform(delete("/api/v1/restaurants/{restaurantId}", restaurantId))
                .andExpect(status().isOk());
    }
}