package com.app.mfi2.auth;

import com.app.mfi2.auth.dto.AuthenticationRequest;
import com.app.mfi2.auth.dto.AuthentificationResponse;
import com.app.mfi2.auth.dto.RegisterRequest;
import com.app.mfi2.config.JwtService;
import com.app.mfi2.user.model.Admin;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.repository.AdminRepository;
import com.app.mfi2.user.repository.ClientRepository;
import com.app.mfi2.user.repository.ProducerRepository;
import com.app.mfi2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final ProducerRepository producerRepository;

    public AuthentificationResponse register(RegisterRequest request) throws Exception {
        if (request.getRole() == null) {
            return null;
        }

        User user = switch (request.getRole()) {
            case ADMIN -> Admin.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();
            case CLIENT -> Client.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .build();
            case PRODUCER -> Producer.builder()
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(request.getRole())
                    .siret(request.getSiret())
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

        Optional<User> userData = userRepository.findByEmail(request.getEmail());

        if (userData.isEmpty()) {
            return new AuthentificationResponse("Invalid email or password");
        }

        User user = userData.get();

        var token = jwtService.generateToken(user);
        return AuthentificationResponse.builder()
                .token(token)
                .build();
    }
}
