package com.renaudk.auth_service.dto;


import com.renaudk.auth_service.entity.Role;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserEntitiesDto {


    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
}
