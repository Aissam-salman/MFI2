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
 * The type Order.
 */
@Entity
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    private Date date;

    @OneToMany
    private List<Item> items;
    private double totalPrice;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId=" + client.getId() +
                ", date=" + date +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
