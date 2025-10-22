package com.renaudk.controller;


import com.renaudk.model.Vehicle;
import com.renaudk.model.VehicleDto;
import com.renaudk.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles(){

        return ResponseEntity.ok(vehicleService.getVehicles());

    }

    @PostMapping
    public ResponseEntity<Vehicle> create (@RequestBody VehicleDto vehicleDto){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehicleService.create(vehicleDto));

    }
    @PostMapping("{vehicleId}/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void associate(@PathVariable String vehicleId, @PathVariable Long userId){
        vehicleService.validateVehicle(vehicleId);

        vehicleService.associate(vehicleId, userId);

    }
    @PatchMapping("{vehicleId}/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable String vehicleId, @PathVariable Long userId){
        vehicleService.validateVehicle(vehicleId);


        vehicleService.removeAssociation(vehicleId, userId);

    }
}
