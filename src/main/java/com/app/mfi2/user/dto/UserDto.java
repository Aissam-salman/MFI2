package com.app.mfi2.user.dto;

import com.app.mfi2.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private Role role;
}
