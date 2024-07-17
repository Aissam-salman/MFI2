package com.app.mfi2.dto;

import com.app.mfi2.model.Item;
import lombok.Data;

/**
 * The type List item dto.
 */
@Data
public class ListItemDto {
    private String name;
    private String description;
    private int quantity;
    private double price;

    /**
     * Instantiates a new List item dto.
     */
    public ListItemDto() {
    }

    /**
     * Instantiates a new List item dto.
     *
     * @param item the item
     */
    public ListItemDto(Item item) {
        this.name = item.getProduct().getName();
        this.description = item.getProduct().getDescription();
        this.quantity = item.getQuantity();
        this.price = item.getProduct().getPrice() * item.getQuantity();
    }
}
