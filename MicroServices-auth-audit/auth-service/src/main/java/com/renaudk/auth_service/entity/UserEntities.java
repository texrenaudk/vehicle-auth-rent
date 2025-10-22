package com.renaudk.auth_service.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Email
    private String email;

    @Size(min = 8, max = 80, message = "{Size.UserEntities.password.validation}")
    private String password;

    @NotNull(message = "{NotNull.UserEntities.firstname.validation}" )
    private String firstName;

    @NotNull (message = "{NotNull.UserEntities.lastname.validation}" )
    private String lastName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Set<Role> roles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private boolean enabled;
}
