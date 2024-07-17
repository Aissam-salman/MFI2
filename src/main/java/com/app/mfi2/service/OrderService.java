package com.app.mfi2.service;

import com.app.mfi2.dto.OrderDto;
import com.app.mfi2.model.Cart;
import com.app.mfi2.model.ECartStatus;
import com.app.mfi2.model.Item;
import com.app.mfi2.model.Order;
import com.app.mfi2.repository.CartRepository;
import com.app.mfi2.repository.OrderRepository;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.User;
import com.app.mfi2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Order service.
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    /**
     * Create order from cart order.
     *
     * @param userId the user id
     * @return the order
     */
    @Transactional
    public OrderDto createOrderFromCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found in Carts"));
        Cart cart = cartRepository.findCartByClientId(userId);
        if (cart == null) throw new RuntimeException("Cart not found");

        if (cart.getStatus() != ECartStatus.VALIDATE) return null;

        List<Item> orderItems = cart.getItems().stream().map(item -> new Item(item.getQuantity(), item.getProduct())).toList();

        Order order = Order.builder()
                .client((Client) user)
                .items(orderItems)
                .date(cart.getDate())
                .totalPrice(orderItems.stream().mapToDouble(item ->
                                        item.getProduct().getPrice() * item.getQuantity()
                                )
                                .sum()
                )
                .build();

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return new OrderDto(savedOrder.getId(), savedOrder.getClient(), savedOrder.getDate(), savedOrder.getTotalPrice());
    }

    /**
     * Retrieves all orders for a specific user.
     *
     * @param userId the user ID
     * @return a list of orders for the user
     * @throws RuntimeException if the user is not found
     */
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByClientId(userId);
    }

    /**
     * Retrieves a specific order by its ID.
     *
     * @param orderId the order ID
     * @return the order
     * @throws RuntimeException if the order is not found
     */
    @Transactional(readOnly = true)
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
