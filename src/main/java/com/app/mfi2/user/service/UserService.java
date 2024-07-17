package com.app.mfi2.user.service;

import com.app.mfi2.auth.dto.AuthentificationResponse;
import com.app.mfi2.config.JwtService;
import com.app.mfi2.user.dto.UserUpdateDto;
import com.app.mfi2.user.model.EProducerStatus;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Gets all users.
     *
     * @return the all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Update user authentification response.
     *
     * @param userId  the user id
     * @param request the request
     * @return the authentification response
     */
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
            log.error(e.getMessage());
            return null;
        }
    }

    public AuthentificationResponse updateStatusProducer(Long userId, EProducerStatus status) {
        try {
            User user = getUserById(userId);
            Producer producer = (Producer) user;
            producer.setStatus(status);
            userRepository.save(producer);
            var jwtToken = jwtService.generateToken(new HashMap<>(), producer);
            return AuthentificationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * Delete user boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
