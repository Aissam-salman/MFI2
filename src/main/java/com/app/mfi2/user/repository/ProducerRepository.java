package com.app.mfi2.user.repository;

import com.app.mfi2.user.model.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Producer repository.
 */
public interface ProducerRepository extends JpaRepository<Producer, Long> {
    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Producer> findByEmail(String email);
}
