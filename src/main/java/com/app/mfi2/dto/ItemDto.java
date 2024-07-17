package com.app.mfi2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Item dto.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    @NotNull
    private Long userId;
    @NotNull
    private Long productId;
    @Size(min = 1)
    private int quantity;
}
