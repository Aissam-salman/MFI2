package com.app.mfi2.controller;

import com.app.mfi2.dto.OrderDto;
import com.app.mfi2.model.Order;
import com.app.mfi2.service.OrderService;
import com.app.mfi2.utils.MapperDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Order controller.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Bon de commande")
@CrossOrigin(value = "*")
public class OrderController {

    private final OrderService orderService;

    /**
     * Create order from cart response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @PostMapping
    @ResponseBody
    @Operation(summary = "créer une commande avec son panier (userId)")
    public ResponseEntity<OrderDto> createOrderFromCart(@RequestBody Long userId) {
        OrderDto or = orderService.createOrderFromCart(userId);
        if (or != null) {
            return ResponseEntity.ok(or);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * List orders by user id response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @GetMapping("/user/{userId}")
    @ResponseBody
    @Operation(summary = "Trouver liste de commande par rapport à son userId")
    public ResponseEntity<List<OrderDto>> listOrdersByUserId(@PathVariable("userId") Long userId) {
        List<OrderDto> orders =
                orderService.getOrdersByUserId(userId).stream().map(order -> {
                    OrderDto orderDto = MapperDTO.convertToDto(order,
                            OrderDto.class);
                    orderDto.setUserId(order.getClient().getId());
                    return orderDto;
                }).toList();

        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }

    /**
     * Gets order by id.
     *
     * @param orderId the order id
     * @return the order by id
     */
    @GetMapping("/{orderId}")
    @ResponseBody
    @Operation(summary = "Trouver une commande grace à son Id")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        OrderDto orderDto = MapperDTO.convertToDto(order, OrderDto.class);
        orderDto.setUserId(order.getClient().getId());

        return ResponseEntity.ok(orderDto);
    }
}
