package com.app.mfi2.repository;

import com.app.mfi2.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The interface Item repository.
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    /**
     * Find item by product id list.
     *
     * @param productId the product id
     * @return the list
     */
    @Query("select p from Item p where p.id = ?1")
    List<Item> findItemByProductId(Long productId);

    /**
     * Delete by product id.
     *
     * @param productId the product id
     */
    @Modifying
    @Transactional
    @Query("delete from Item i where i.product.id = :productId")
    void deleteByProductId(Long productId);

}
