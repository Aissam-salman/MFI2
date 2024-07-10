package com.app.mfi2.user.repository;

import com.app.mfi2.user.model.Admin;
import com.app.mfi2.user.model.Client;
import com.app.mfi2.user.model.Producer;
import com.app.mfi2.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByEmail(String email);
}
