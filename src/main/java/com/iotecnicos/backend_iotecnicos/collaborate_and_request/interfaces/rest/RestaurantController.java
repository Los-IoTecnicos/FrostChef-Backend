package com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest;

import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.commands.DeleteRestaurantCommand;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.model.queries.GetRestaurantDetails;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantCommandService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.domain.services.RestaurantQueryService;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.CreateRestaurantResource;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.RestaurantResource;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.resources.UpdateRestaurantResource;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform.CreateRestaurantCommandFromResourceAssembler;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform.RestaurantResourceFromEntityAssembler;
import com.iotecnicos.backend_iotecnicos.collaborate_and_request.interfaces.rest.transform.UpdateRestaurantCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value="/api/v1/restaurant",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Restaurant", description = "Restaurant Management Endpoints")
public class RestaurantController {

    private final RestaurantCommandService restaurantCommandService;
    private final RestaurantQueryService restaurantQueryService;

    public RestaurantController(RestaurantCommandService restaurantCommandService, RestaurantQueryService restaurantQueryService){
        this.restaurantCommandService = restaurantCommandService;
        this.restaurantQueryService = restaurantQueryService;
    }

    @PostMapping
    public ResponseEntity<RestaurantResource> createRestaurant(@RequestBody CreateRestaurantResource createRestaurantResource){
        var createRestaurantCommand = CreateRestaurantCommandFromResourceAssembler.toCommandFromResource(createRestaurantResource);
        var restaurantId = restaurantCommandService.handle(createRestaurantCommand);
        if (restaurantId == 0L){
            return ResponseEntity.badRequest().build();
        }
        var getRestaurantByIdQuery = new GetRestaurantDetails(restaurantId);
        var restaurant = restaurantQueryService.handle(getRestaurantByIdQuery);
        if (restaurant.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var restaurantResource = RestaurantResourceFromEntityAssembler.toResourceFromEntity(restaurant.get());
        return  new ResponseEntity<>(restaurantResource, HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResource> getRestaurant(@PathVariable Long restaurantId){
        var getRestaurantByIdQuery = new GetRestaurantDetails(restaurantId);
        var restaurant = restaurantQueryService.handle(getRestaurantByIdQuery);
        if(restaurant.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var restaurantResource = RestaurantResourceFromEntityAssembler.toResourceFromEntity(restaurant.get());
        return ResponseEntity.ok(restaurantResource);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResource> updateRestaurant(@PathVariable Long restaurantId, @RequestBody UpdateRestaurantResource updateRestaurantResource){
        var updateRestaurantCommand = UpdateRestaurantCommandFromResourceAssembler.toCommandFromResource(restaurantId, updateRestaurantResource);
        var updateRestaurant = restaurantCommandService.handle(updateRestaurantCommand);
        if (updateRestaurant.isEmpty()){
            return  ResponseEntity.badRequest().build();
        }
        var restaurantResource = RestaurantResourceFromEntityAssembler.toResourceFromEntity(updateRestaurant.get());
        return ResponseEntity.ok(restaurantResource);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long restaurantId){
        var deleteRestaurantCommand = new DeleteRestaurantCommand(restaurantId);
        restaurantCommandService.handle(deleteRestaurantCommand);
        return ResponseEntity.ok("Restaurant deleted successfully");
    }
}
