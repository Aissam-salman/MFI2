package com.app.mfi2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Item.
 */
@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    /**
     * Instantiates a new Item.
     *
     * @param quantity the quantity
     * @param product  the product
     */
    public Item(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", product=" + product.getName() +
                ", quantity=" + quantity +
                '}';
    }
}
