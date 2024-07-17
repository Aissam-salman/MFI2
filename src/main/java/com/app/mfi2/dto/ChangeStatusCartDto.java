package com.app.mfi2.dto;

import com.app.mfi2.model.ECartStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Change status cart dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusCartDto {
    @NotNull
    private Long userId;
    private ECartStatus status;
}
