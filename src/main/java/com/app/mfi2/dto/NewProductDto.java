package com.app.mfi2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * The type New product dto.
 */
@Data
@Builder
@AllArgsConstructor
public class NewProductDto {
    private String name;
    private String description;
    private Double price;
    private Long producerId;
}
