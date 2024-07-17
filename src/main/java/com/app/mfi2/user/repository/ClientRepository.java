package com.app.mfi2.user.repository;

import com.app.mfi2.user.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Client repository.
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
    /**
     * Find by email optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<Client> findByEmail(String email);
}
