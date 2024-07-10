package com.app.mfi2.auth;

import com.app.mfi2.config.JwtService;
import com.app.mfi2.user.model.Admin;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthentificationResponse register(RegisterRequest request) throws Exception {
        User user = switch (request.getRole()) {
            case ADMIN -> Admin.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            case CLIENT -> Client.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
            case PRODUCER -> Producer.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();
        };

        assert user != null;

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(new HashMap<>(), user);

        return AuthentificationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthentificationResponse authenticate(AuthenticationRequest request) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);
        return AuthentificationResponse.builder()
                .token(token)
                .build();
    }
}
