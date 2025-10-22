package com.renaudk.auth_service.repository;

import com.renaudk.auth_service.entity.UserEntities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntitiesRepository extends JpaRepository<UserEntities, Long> {
    boolean existsByEmail(String email);


    Optional <UserEntities> findByEmail(String email);
}
