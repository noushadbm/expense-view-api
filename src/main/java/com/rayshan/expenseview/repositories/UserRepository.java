package com.rayshan.expenseview.repositories;

import com.rayshan.expenseview.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
