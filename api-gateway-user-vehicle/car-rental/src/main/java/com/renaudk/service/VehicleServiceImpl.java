package com.renaudk.service;

import com.renaudk.exception.VehicleNotFoundException;
import com.renaudk.model.Status;
import com.renaudk.model.Vehicle;
import com.renaudk.model.VehicleDto;
import com.renaudk.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public List<Vehicle> getVehicles() {
        return StreamSupport
                .stream(vehicleRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    @Override
    public Vehicle create(VehicleDto vehicleDto) {
        var vehicle = new Vehicle();
        vehicle.setStatus(Status.AVAILABLE);
        vehicle.setBrand(vehicleDto.getBrand());
        vehicle.setModel(vehicleDto.getModel());
        vehicle.setOwner(null);
        vehicle.setAssociationDate(null);

        return vehicleRepository.save(vehicle);
    }

    @Override
    public void validateVehicle(String vehicleId) {
        Optional.of(vehicleId)
                .map(Long::valueOf)
                .flatMap(vehicleRepository::findById)
                .orElseThrow(() ->new VehicleNotFoundException("vehicolo non trovato" + vehicleId));

    }

    @Override
    public void associate(String vehicleID, Long userId) {
        var vehicle = Optional.of(vehicleID)
                .map(Long::valueOf)
                .flatMap(vehicleRepository::findById)
                .filter(v -> v.getStatus() == Status.AVAILABLE)
                .orElseThrow();
        vehicle.setOwner(userId);
        vehicle.setStatus(Status.ASSOCIATED);
        vehicle.setAssociationDate(new Date());

        //update
        vehicleRepository.save(vehicle);

    }

    @Override
    public void removeAssociation(String vehicleID, Long userId) {
        var vehicle = Optional.of(vehicleID)
                .map(Long::valueOf)
                .flatMap(vehicleRepository::findById)
                .filter(v -> v.getStatus() == Status.ASSOCIATED)
                .filter(v -> v.getOwner().equals(userId))
                .orElseThrow();

        vehicle.setOwner(null);
        vehicle.setAssociationDate(null);
        vehicle.setStatus(Status.AVAILABLE);

        //update
        vehicleRepository.save(vehicle);

    }
}
