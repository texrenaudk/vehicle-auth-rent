package com.renaudk.controller;

import com.renaudk.model.Vehicle;
import com.renaudk.model.VehicleDto;
import com.renaudk.proxy.VehicleProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/vehicles")
@RequiredArgsConstructor
public class GatewayVehicleController {
    private final VehicleProxy vehicleProxy;
    
    @GetMapping()
    public Mono<Vehicle[]> getAllVehicles(){
        return vehicleProxy.getAllVehicle();
    }

    @PostMapping
    public Mono<Vehicle> createVehicle(@RequestBody VehicleDto vehicleDto){
        return vehicleProxy.createVehicle(vehicleDto);
    }

    @PostMapping("{vehicleId}/users/{userId}")


    public Mono<Vehicle> Associate(@PathVariable String vehicleId, @PathVariable Long userId){

       return vehicleProxy.AssociationUserVehicle(vehicleId, userId);
    }
}
