package com.rayshan.expenseview.repositories;

import com.rayshan.expenseview.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    List<UserEntity> findByUserName(String userName);

    @Query("SELECT MAX(u.id) FROM UserEntity u")
    Integer findMaxUserId();
}
