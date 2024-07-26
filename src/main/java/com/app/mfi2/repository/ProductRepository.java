package com.app.mfi2.repository;

import com.app.mfi2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The interface Product repository.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Product> findByName(String name);

    //TODO: add find products less than ... price

    /**
     * Delete product by id.
     *
     * @param productId the product id
     */
    @Modifying
    @Transactional
    @Query(value = "WITH deleted_items AS (" + "  DELETE FROM item WHERE product_id = :productId RETURNING id" + "), " + "deleted_cart_items AS (" + "  DELETE FROM cart_items WHERE items_id IN (SELECT id FROM deleted_items)" + ")" + "DELETE FROM product WHERE id = :productId", nativeQuery = true)
    void deleteProductById(Long productId);
}
