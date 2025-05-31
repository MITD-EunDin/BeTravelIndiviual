package com.example.PjTravelMitd.mitd_sample_code.repository;


import com.example.PjTravelMitd.mitd_sample_code.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findByRolesContaining(String role);
    boolean existsByUsername(String username);
}
