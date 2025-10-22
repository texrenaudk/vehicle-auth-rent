package com.renaudk.auth_service.controller;

import com.renaudk.auth_service.dto.JwtResponseDto;
import com.renaudk.auth_service.dto.LoginRequestDto;
import com.renaudk.auth_service.dto.RegisterRequestDto;
import com.renaudk.auth_service.dto.UserEntitiesDto;
import com.renaudk.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserEntitiesDto> register(@RequestBody RegisterRequestDto dto) {
        UserEntitiesDto registeredUser = authService.register(dto);
        log.info("enregistrement de {} un utilisateur", registeredUser.getFirstName());
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        log.info("tentative de login de {}", loginRequestDto.getEmail());
        JwtResponseDto token = authService.login(loginRequestDto);
        return ResponseEntity.ok(token);


    }
}
