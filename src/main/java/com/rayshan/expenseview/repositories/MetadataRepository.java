package com.rayshan.expenseview.repositories;

import com.rayshan.expenseview.entities.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    Metadata findByIdAndUserId(Integer id, Integer userId);

    List<Metadata> findByUserIdAndStatusOrderByIdDesc(Integer userId, String status);
}
