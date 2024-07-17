package com.app.mfi2.repository;

import com.app.mfi2.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Order repository.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find by client id order.
     *
     * @param clientId the client id
     * @return List orders
     */
    List<Order> findByClientId(Long clientId);
}
