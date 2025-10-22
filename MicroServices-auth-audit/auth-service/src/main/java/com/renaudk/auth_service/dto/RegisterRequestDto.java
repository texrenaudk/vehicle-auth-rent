package com.renaudk.auth_service.dto;


import com.renaudk.auth_service.entity.Role;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Set<Role> roles;


}