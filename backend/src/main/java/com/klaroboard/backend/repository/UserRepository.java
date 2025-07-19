package com.klaroboard.backend.repository;

import com.klaroboard.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Tells Spring this is a repository bean
public interface UserRepository extends JpaRepository<User, Long> {
    // By extending JpaRepository, we get methods like:
    // save(), findById(), findAll(), deleteById(), etc.
    // all for free, for our User entity.
}