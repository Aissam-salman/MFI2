package com.app.mfi2.controller;


import com.app.mfi2.auth.dto.AuthentificationResponse;
import com.app.mfi2.user.Role;
import com.app.mfi2.user.dto.UserDto;
import com.app.mfi2.user.dto.UserListDto;
import com.app.mfi2.user.dto.UserUpdateDto;
import com.app.mfi2.user.model.EProducerStatus;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.service.UserService;
import com.app.mfi2.utils.MapperDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type User controller.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "API gestion utilisateurs")
@CrossOrigin(value = "*")
public class UserController {
    private final UserService userService;

    /**
     * Gets all users.
     *
     * @return the all users
     */
    @GetMapping
    @ResponseBody
    @Operation(summary = "Liste des users")
    public ResponseEntity<List<UserListDto>> getAllUsers() {
        List<UserListDto> userListDtos = userService.getAllUsers().stream()
                .filter(user -> user.getRole() != Role.ADMIN)
                .map(user -> MapperDTO.convertToDto(user, UserListDto.class)).toList();
        return ResponseEntity.ok(userListDtos);
    }

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    @GetMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Trouver un utilisateur grace à son Id")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }

        if (user.getRole() == Role.ADMIN) {
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.ok(MapperDTO.convertToDto(userService.getUserById(id), UserDto.class));
    }

    /**
     * Update user response entity.
     *
     * @param id          the id
     * @param updatedUser the updated user
     * @return the response entity
     */
    @PutMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Mettre à jours les informations de l'utilisateur")
    public ResponseEntity<AuthentificationResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto updatedUser) {
        return ResponseEntity.ok(userService.updateUser(id, updatedUser));
    }

    /**
     * Update user producer response entity.
     *
     * @param id     the id
     * @param status the status
     * @return the response entity
     */
    @PutMapping("/producer/{id}")
    @ResponseBody
    @Operation(summary = "Changer le status d'un producteur")
    public ResponseEntity<AuthentificationResponse> updateUserProducer(@PathVariable Long id,
                                                                       @RequestBody EProducerStatus status) {
        return ResponseEntity.ok(userService.updateStatusProducer(id, status));
    }

    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(summary = "Supprimer un utilisateur selon son Id")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
        boolean result = userService.deleteUser(id);
        if (result) return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }
}
