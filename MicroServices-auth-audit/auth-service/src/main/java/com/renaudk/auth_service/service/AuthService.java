package com.renaudk.auth_service.service;

import com.renaudk.auth_service.dto.JwtResponseDto;
import com.renaudk.auth_service.dto.LoginRequestDto;
import com.renaudk.auth_service.dto.RegisterRequestDto;
import com.renaudk.auth_service.entity.Role;
import com.renaudk.auth_service.entity.UserEntities;
import com.renaudk.auth_service.dto.UserEntitiesDto;
import com.renaudk.auth_service.mapper.UserMapper;
import com.renaudk.auth_service.repository.UserEntitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserEntitiesRepository userEntitiesRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

public UserEntitiesDto register(RegisterRequestDto dto){

    if (userEntitiesRepository.existsByEmail(dto.getEmail())) {
        throw new RuntimeException("l'email existe deja"); // Ou custom exception
    }

    UserEntities userEntities = new UserEntities();
    userEntities.setEmail(dto.getEmail());
    userEntities.setPassword(passwordEncoder.encode(dto.getPassword()));
    userEntities.setFirstName(dto.getFirstName());
    userEntities.setLastName(dto.getLastName());
    userEntities.setRoles(Set.of(Role.ROLE_USER));

    UserEntities saved = userEntitiesRepository.save(userEntities);
    return userMapper.toDto(saved);
}
   public JwtResponseDto login(LoginRequestDto request) {

        UserEntities user = userEntitiesRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe invalide");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new JwtResponseDto(token);
    }

}
