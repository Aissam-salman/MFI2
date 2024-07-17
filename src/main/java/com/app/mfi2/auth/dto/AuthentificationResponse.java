package com.app.mfi2.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Authentification response.
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AuthentificationResponse {
    private String token;
}
