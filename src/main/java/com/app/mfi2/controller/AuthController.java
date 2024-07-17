package com.app.mfi2.controller;

import com.app.mfi2.auth.AuthService;
import com.app.mfi2.auth.dto.AuthenticationRequest;
import com.app.mfi2.auth.dto.AuthentificationResponse;
import com.app.mfi2.auth.dto.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Auth controller.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "login")
@CrossOrigin(value = "*")
public class AuthController {
    private final AuthService authService;

    /**
     * Register response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @SneakyThrows
    @PostMapping("/signup")
    @Operation(summary = "créer un compte utilisateur")
    public ResponseEntity<AuthentificationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Login response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @SneakyThrows
    @PostMapping("/login")
    @Operation(summary = "Connexion à un compte")
    public ResponseEntity<AuthentificationResponse> login(@RequestBody AuthenticationRequest request) {
        //TODO: add switch case role redirect
        return ResponseEntity.ok(authService.authenticate(request));
    }


}