package com.app.mfi2.auth.dto;

import com.app.mfi2.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String firstname;
    private String lastname;
    @Email
    private String email;
    private String password;
    private Role role;
    @NotNull
    private String siret;
}
