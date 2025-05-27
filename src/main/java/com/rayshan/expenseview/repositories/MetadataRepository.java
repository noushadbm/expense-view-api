package com.rayshan.expenseview.repositories;

import com.rayshan.expenseview.entities.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetadataRepository extends JpaRepository<Metadata, Long> {
    Metadata findByIdAndUserId(Integer id, Integer userId);
}
