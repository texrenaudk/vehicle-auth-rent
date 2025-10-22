package com.renaudk.repository;

import com.renaudk.model.Vehicle;
import com.renaudk.model.VehicleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Long> {


}
