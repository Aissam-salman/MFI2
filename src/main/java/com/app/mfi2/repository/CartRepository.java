package com.app.mfi2.repository;

import com.app.mfi2.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * The interface Cart repository.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * Find cart by client id cart.
     *
     * @param clientId the client id
     * @return the cart
     */
    @Query("select p from Cart p where p.client.id = ?1")
    Cart findCartByClientId(Long clientId);
}
