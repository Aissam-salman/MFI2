package com.app.mfi2.user.service;

import com.app.mfi2.auth.dto.AuthentificationResponse;
import com.app.mfi2.config.JwtService;
import com.app.mfi2.user.dto.UserUpdateDto;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public AuthentificationResponse updateUser(Long userId, UserUpdateDto request) {
        User user = null;
        try {
            Optional<User> userData = userRepository.findById(userId);
            if (userData.isPresent()) {
                user = userData.get();
            }
            assert user != null;
            if (request.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getFirstname() != null) {
                user.setFirstname(request.getFirstname());
            }
            if (request.getLastname() != null) {
                user.setLastname(request.getLastname());
            }
            userRepository.save(user);

            var jwtToken = jwtService.generateToken(new HashMap<>(), user);

            return AuthentificationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
