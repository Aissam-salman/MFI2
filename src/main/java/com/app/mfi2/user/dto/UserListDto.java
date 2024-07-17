package com.app.mfi2.user.dto;

import com.app.mfi2.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type User list dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserListDto {
    private String firstname;
    private String lastname;
    private String email;
    private Role role;

}
