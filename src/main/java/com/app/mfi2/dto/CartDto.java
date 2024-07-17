package com.app.mfi2.dto;

import com.app.mfi2.model.ECartStatus;
import com.app.mfi2.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * The type Cart dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long id;
    private UserDto client;
    private Date date;
    private ECartStatus status;
}
