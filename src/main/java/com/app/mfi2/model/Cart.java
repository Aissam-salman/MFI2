package com.app.mfi2.model;

import com.app.mfi2.user.model.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * The type Cart.
 */
@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private Date date;
    private ECartStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    /**
     * Add item.
     *
     * @param item the item
     */
    public void addItem(Item item) {
        this.items.add(item);
    }

    /**
     * Remove item.
     *
     * @param item the item
     */
    public void removeItem(Item item) {
        this.items.remove(item);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", clientId=" + client.getId() +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
