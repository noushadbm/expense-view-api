package com.rayshan.expenseview.repositories;

import com.rayshan.expenseview.entities.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthEntity, Integer> {
    // Define any custom query methods if needed
}
