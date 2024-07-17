package com.app.mfi2.dto;

import com.app.mfi2.user.model.Client;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The type Order dto.
 */
@Data
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Date date;
    private double totalPrice;

    /**
     * Instantiates a new Order dto.
     *
     * @param id         the id
     * @param client     the client
     * @param date       the date
     * @param totalPrice the total price
     */
    public OrderDto(Long id, Client client, Date date, double totalPrice) {
        this.id = id;
        this.userId = client.getId();
        this.date = date;
        this.totalPrice = totalPrice;
    }
}
