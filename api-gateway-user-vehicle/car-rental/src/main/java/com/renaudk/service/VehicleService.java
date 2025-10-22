package com.renaudk.service;

import com.renaudk.model.Vehicle;
import com.renaudk.model.VehicleDto;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getVehicles();
    Vehicle create(VehicleDto vehicleDto);
    void validateVehicle (String vehicleId);
    void associate(String vehicleID, Long userId);
    void removeAssociation(String vehicleID, Long userId);

}
